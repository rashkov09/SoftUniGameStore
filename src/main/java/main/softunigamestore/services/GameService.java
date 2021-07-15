package main.softunigamestore.services;

import main.softunigamestore.entities.Game;
import main.softunigamestore.repositories.GameRepository;

public interface GameService {


    void addGame(Game game);

    void editGame(long id, String[] data) throws Exception;
}
