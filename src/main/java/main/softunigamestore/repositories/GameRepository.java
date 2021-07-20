package main.softunigamestore.repositories;

import main.softunigamestore.entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game,Long> {
    Game findGameById(long id);

    void deleteById(long id);

    List<Game> findAll();

    Game getByTitle(String title);
}
