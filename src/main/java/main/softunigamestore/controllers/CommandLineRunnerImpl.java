package main.softunigamestore.controllers;

import main.softunigamestore.entities.User;
import main.softunigamestore.entities.validators.EmailConstraintValidator;
import main.softunigamestore.entities.validators.PasswordConstraintValidator;
import main.softunigamestore.services.serviceImpl.GameServiceImpl;
import main.softunigamestore.services.serviceImpl.OrderServiceImpl;
import main.softunigamestore.services.serviceImpl.UserServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.SQLIntegrityConstraintViolationException;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {
    private final UserServiceImpl userService;
    private final GameServiceImpl gameService;
    private final OrderServiceImpl orderService;
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private final EmailConstraintValidator emailValidator;
    private final PasswordConstraintValidator passwordValidator;
    private boolean isLoggedIn = false;
    private User currentUser = null;

    public CommandLineRunnerImpl(UserServiceImpl userService, GameServiceImpl gameService, OrderServiceImpl orderService, EmailConstraintValidator emailValidator, PasswordConstraintValidator passwordValidator) {
        this.userService = userService;
        this.gameService = gameService;
        this.orderService = orderService;
        this.emailValidator = emailValidator;
        this.passwordValidator = passwordValidator;
    }

    @Override
    public void run(String... args) throws Exception {


        while (true) {
            System.out.println("Welcome to SoftUni Game Store!");
            System.out.println("Type Exit to close the application.");
            System.out.print("Input: ");
            String[] data = reader.readLine().split("\\|");
            if (data.length == 1 && data[0].equals("Exit")) {
                break;
            }
            switch (data[0]) {
                case "LoginUser" -> userLogIn(data);
                case "RegisterUser" -> userRegister(data);
                case "Logout" -> logout();
                default -> System.out.println("Wrong input, please try again!");
            }
        }
    }

    private void logout() {
        if (isLoggedIn){
            isLoggedIn = false;
            currentUser = null;
            System.out.println("User Ivan successfully logged out");
        }else {
            System.out.println("Cannot log out. No user was logged in.");
        }
    }

    private void userLogIn(String[] data) {
        String email = data[1];
        String password = data[2];
        User user = userService.getUserByEmail(email);
        if (user != null && user.getPassword().equals(password)){
                currentUser = user;
                isLoggedIn= true;
                System.out.printf("Successfully logged in %s",currentUser.getFullName());
        } else {
            System.out.println("Incorrect username / password");
        }
    }

    private void userRegister(String[] data) throws SQLIntegrityConstraintViolationException {
        String email = data[1];
        String password = data[2];
        String confirmPassword = data[3];
        String fullName = data[4];
        try {
            if (emailValidator.isValid(email)) {
                if (passwordValidator.isValid(password, confirmPassword)) {
                    User user = new User(email, password, fullName);
                    user.setAdministrator(userService.getUsers().size() == 0);

                    userService.addUser(user);
                    System.out.printf("%s was registered", user.getFullName());

                } else {
                    System.out.println(passwordValidator.getMessage());
                }
            } else {
                System.out.println(emailValidator.getMessage());
            }
        }catch (Exception e){
           System.out.println("Current email already exists!");
        }
    }
}
