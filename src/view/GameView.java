package view;

import model.entities.ItalianCard.Suit;
import model.entities.Play;

/**
 * A GUI for a turn based italian card game.
 */
public interface GameView {
    /**
     * Allows the players to make is play and returns it.
     * @return the play made by the user
     */
    Play getUserPlay();

    /**
     * Allows the players to select a Briscola suit and returns it.
     * @return the suit selected by the user
     */
    Suit getSelectedBriscola();

    /**
     * Show the current round-play done.
     */
    void renderPlay();
}

/*
 * LA CLASSE CHE IMPLEMENTA QUESTA INTERFACCIA PRENDE DA COSTRUTTORE UN MATCH
 */
