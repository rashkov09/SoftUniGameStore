package main.softunigamestore.services;

import main.softunigamestore.entities.Game;
import main.softunigamestore.repositories.GameRepository;

import java.util.Collection;
import java.util.List;

public interface GameService {


    void addGame(Game game);

    void editGame(long id, String[] data) throws Exception;

    void deleteGame(long id) throws Exception;

    Game getGame(long id);

    List<Game> getAllGames();

    Game getGameByTitle(String title);
}
