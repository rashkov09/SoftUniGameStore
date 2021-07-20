package main.softunigamestore.services.serviceImpl;

import main.softunigamestore.entities.Game;
import main.softunigamestore.repositories.GameRepository;
import main.softunigamestore.services.GameService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Service
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;

    public GameServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public void addGame(Game game) {
        gameRepository.save(game);
    }

    @Override
    public void editGame(long id, String[] data) throws Exception {
        try {
            Game game = gameRepository.findGameById(id);
            Arrays.stream(data).forEach(value -> {
                String[] currentElementData = value.split("=");
                String parameterName = currentElementData[0];
                switch (parameterName) {
                    case "title" -> game.setTitle(currentElementData[1]);
                    case "price" -> game.setPrice(new BigDecimal(currentElementData[1]));
                    case "size" -> game.setSize(Double.parseDouble(currentElementData[1]));
                    case "trailer" -> game.setTrailer(currentElementData[1]);
                    case "imageThumbnail" -> game.setImageThumbnail(currentElementData[1]);
                    case "description" -> game.setDescription(currentElementData[1]);
                }
            });
            gameRepository.save(game);
        }catch (Exception e) {
            throw new Exception("Something went wrong! Please try again.");
        }
    }

    @Override
    public void deleteGame(long id) throws Exception {
        try{
            gameRepository.deleteById(id);
        }catch (Exception e){
            throw new Exception("Invalid game id.Please try again!");
        }
    }

    @Override
    public Game getGame(long id) {
        return gameRepository.findGameById(id);
    }

    @Override
    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    @Override
    public Game getGameByTitle(Game game) {
        return gameRepository.getByTitle(game.getTitle());
    }
}
