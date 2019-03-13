package view;

import model.entities.ItalianCard.Suit;
import model.entities.Play;

/**
 * A GUI for a turn based italian card game.
 */
public interface GameView {
    /**
<<<<<<< HEAD
=======
     * Allows the players to make is play and returns it.
>>>>>>> 7eb43f054bc909f4f1888080192bc3065642fac6
     * @return the play made by the user
     */
    Play getUserPlay();

    /**
<<<<<<< HEAD
=======
     * Allows the players to select a Briscola suit and returns it.
>>>>>>> 7eb43f054bc909f4f1888080192bc3065642fac6
     * @return the suit selected by the user
     */
    Suit getSelectedBriscola();

    /**
<<<<<<< HEAD
     * @param the player whose play has already been made by the AI
     * @param the play made by the AI
=======
     * Show the current round-play done.
>>>>>>> 7eb43f054bc909f4f1888080192bc3065642fac6
     */
    void renderPlay();
}

/*
 * LA CLASSE CHE IMPLEMENTA QUESTA INTERFACCIA PRENDE DA COSTRUTTORE UN MATCH
 */
