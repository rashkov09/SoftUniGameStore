package main.softunigamestore.controllers;

import main.softunigamestore.entities.Game;
import main.softunigamestore.entities.User;
import main.softunigamestore.entities.validators.EmailConstraintValidator;
import main.softunigamestore.entities.validators.GameValidator;
import main.softunigamestore.entities.validators.PasswordConstraintValidator;
import main.softunigamestore.services.serviceImpl.GameServiceImpl;
import main.softunigamestore.services.serviceImpl.OrderServiceImpl;
import main.softunigamestore.services.serviceImpl.UserServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Executable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

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

    public CommandLineRunnerImpl(UserServiceImpl userService, GameServiceImpl gameService, OrderServiceImpl orderService, GameServiceImpl gameService1, OrderServiceImpl orderService1, EmailConstraintValidator emailValidator, PasswordConstraintValidator passwordValidator) {
        this.userService = userService;
        this.gameService = gameService1;
        this.orderService = orderService1;
        this.emailValidator = emailValidator;
        this.passwordValidator = passwordValidator;
    }

    @Override
    public void run(String... args) throws Exception {


        while (true) {
            System.out.println("Welcome to SoftUni Game Store!");
            System.out.println("Type Exit to close the application.");
            System.out.print("Command: ");
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

    private void userLogIn(String[] data) throws IOException {
        String email = data[1];
        String password = data[2];
        User user = userService.getUserByEmail(email);
        if (user != null && user.getPassword().equals(password)){
                currentUser = user;
                isLoggedIn= true;
                System.out.printf("Successfully logged in %s\n",currentUser.getFullName());

          startUserInterface();
        } else {
            System.out.println("Incorrect username / password");
        }
    }

    private void startUserInterface() throws IOException {
        while (true) {
            System.out.print("Command: ");
            String[] data = reader.readLine().split("\\|");
            if (data.length == 1 && data[0].equals("Exit")) {
                break;
            }
            switch (data[0]) {
                case "AddGame" -> addGame(Arrays.copyOfRange(data,1,data.length));
                case "EditGame" -> editGame(Long.parseLong(data[1]),Arrays.copyOfRange(data,2,data.length));
                case "DeleteGame" -> deleteGame(Long.parseLong(data[1]));
                default -> System.out.println("Wrong command please try again or type Exit.");
            }

        }
    }

    private void deleteGame(long id) {
            try{
                Game game = gameService.getGame(id);
                gameService.deleteGame(id);
                System.out.printf("Deleted %s\n",game.getTitle());
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
    }

    private void editGame(long id, String[] data) {
        try {
            Game game = gameService.getGame(id);
            gameService.editGame(id, data);
            System.out.printf("Edited %s\n", game.getTitle());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }


    private void addGame(String[] data) {
        String title = data[0];
        BigDecimal price = new BigDecimal(data[1]);
        Double size = Double.parseDouble(data[2]);
        String trailer = data[3];
        String thumbnailURL = data[4];
        String description = data[5];
        LocalDate releaseDate = LocalDate.parse(data[6], DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        Game game = new Game(title, price, size, trailer, thumbnailURL, description, releaseDate);
        if(GameValidator.isValid(game)) {
            if (currentUser.getAdministrator()) {
                gameService.addGame(game);
                System.out.printf("Added %s\n",game.getTitle());
            } else {
                orderService.addToShoppingCart(game);
            }
        } else {
            System.out.println(GameValidator.getMessages());
        }
    }

    private void userRegister(String[] data) {
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
                    System.out.printf("%s was registered\n", user.getFullName());

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
