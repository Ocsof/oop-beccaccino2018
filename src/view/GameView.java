package view;

import model.entities.ItalianCard.Suit;
import model.entities.Play;

/**
 * A GUI for a turn based Italian card game.
 */
public interface GameView {
    /**
     * Allows the players to make is play and returns it.
     * 
     * @return the play made by the user
     */
    Play getUserPlay();

    /**
     * Allows the players to select a Briscola suit and returns it.
     * 
     * @return the suit selected by the user
     */
    Suit getSelectedBriscola();

    /**
     * Renders a play made by other entities.
     */
    void renderPlay();
}

