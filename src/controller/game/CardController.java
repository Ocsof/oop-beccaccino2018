package controller.game;

import model.entities.Play;

/**
 * Alessia Rocco 
 * A class that contains the controller of the cardView.
 */
public class CardController {
    Play play;
    /**
     * the ActionListener add to every playable card.
     */
    public void action() {
        System.out.println("carta gicata");
        notify();
    }

}
