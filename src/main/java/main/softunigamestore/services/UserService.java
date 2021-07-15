package main.softunigamestore.services;

import main.softunigamestore.entities.User;

import java.util.List;

public interface UserService {
    List<User> getUsers();

    void addUser(User user) throws Exception;


    User getUserByEmail(String email);
}
