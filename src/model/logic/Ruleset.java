package model.logic;

import java.util.List;
import model.entities.Player;
import view.GameView;

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
     * @return The game view created.
     */
    GameView newGameView(Game game);
    /**
     * This method creates a new Game object.
     * @param name - the name of the player.
     * @return The player created.
     */
    Player newPlayer(String name);
    /**
     * This method creates a new Game object.
     * @return The AI created.
     */
    AIImpl newAI();
}
