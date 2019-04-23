package view;

import model.entities.ItalianCard.Suit;
import model.entities.Play;

/**
 * A GUI for a turn based Italian card game.
 */
public interface GameView {
    /**
     * Allows the user to make its play.
     */
    void allowUserPlay();

    /**
     * Allows the user to select a Briscola suit and returns it.
     * 
     * @return the suit selected by the user
     */
    Suit getSelectedBriscola();

    /**
     * Renders a play made by other entities.
     */
    void update();
}

