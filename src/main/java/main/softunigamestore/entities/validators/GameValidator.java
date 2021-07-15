package main.softunigamestore.entities.validators;

import main.softunigamestore.entities.Game;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class GameValidator {
private static final List<String> messages = new ArrayList<>();

    public static boolean isValid(Game game){
        if (game.getTitle().charAt(0) != game.getTitle().toUpperCase().charAt(0)) {
            messages.add("Game name should start with uppercase letter.");
        }
        if (game.getTitle().length() < 3 || game.getTitle().length() > 100) {
            messages.add("Game title should be between 3 and 100 characters.");
        }
        if (game.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            messages.add("Price must be positive value.");
        }
        if (game.getSize() < 0){
            messages.add("Size must be positive number.");
        }
        if (game.getTrailer().length() != 11){
            messages.add("Game trailer id must be exactly 11 characters long.");
        }
        if (!game.getImageThumbnail().startsWith("http:\\/\\/") && !game.getImageThumbnail().startsWith("https://")) {
         messages.add("Invalid image thumbnail url.");
        }
        if (game.getDescription().length() < 20){
            messages.add("Description must be at least 20 characters long.");
        }
        return messages.size() == 0;


    }

    public static String getMessages(){
        String result = String.join("\n", messages);
        messages.clear();
        return result;
    }
}
