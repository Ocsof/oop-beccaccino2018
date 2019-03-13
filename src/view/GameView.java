package view;

import model.entities.ItalianCard.Suit;
import model.entities.Play;
import model.entities.Player;

/**
 * A GUI for a turn based italian card game.
 */
public interface GameView {
    /**
     * @return the play made by the user
     */
    Play getUserPlay();

    /**
     * @return the suit selected by the user
     */
    Suit getSelectedBriscola();

    /**
     * @param the player whose play has already been made by the AI
     * @param the play made by the AI
     */
    void renderPlay();
}

/*
 * LA CLASSE CHE IMPLEMENTA QUESTA INTERFACCIA PRENDE DA COSTRUTTORE UN MATCH
 */