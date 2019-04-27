package model.logic;

import java.util.List;
import java.util.Optional;

import javafx.stage.Stage;
import model.artificialIntelligence.AI;
import model.entities.Player;
import view.GameViewImpl;

/**
 * The ruleset which creates all the entities and objects for a game.
 */
public interface Ruleset {
    /**
     * This method creates a new Game object.
     * @param players - the list of all the players for the current game.
     * @return The game created.
     */
    Game newGame(List<Player> players);

    /**
     * This method creates a new Game object.
     * @param game - the game for which the view has to be created.
     * @param stage - the stage of the primary scene
     * @return The game view created.
     */
    GameViewImpl newGameView(Game game, Stage stage);
    /**
     * This method creates a new Game object.
     * @param name - the name of the player.
     * @return The player created.
     */
    Player newPlayer(String name);
    /**
     * This method creates a new Game object.
     * @param player - the player that is being controlled by the AI.
     * @param difficulty - the difficulty of the AI to be created.
     * @return The AI created.
     */
    Optional<AI> newAI(Player player, String difficulty);
}
