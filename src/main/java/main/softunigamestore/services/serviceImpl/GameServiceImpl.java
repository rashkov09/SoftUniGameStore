package main.softunigamestore.services.serviceImpl;

import main.softunigamestore.repositories.GameRepository;
import main.softunigamestore.services.GameService;
import org.springframework.stereotype.Service;

@Service
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;

    public GameServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }
}
