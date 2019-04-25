package controller.game;

import java.util.Optional;

import model.entities.ItalianCard;
import model.entities.Play;
import model.entities.PlayImpl;

/**
 * Alessia Rocco 
 * A class that contains the controller of the cardView.
 */
public class CardController {
    private Play play;

    /**
     * the ActionListener add to every playable card.
     * @param c card clicked
     * @param m message selected if present
     */
    public void action(final ItalianCard c, final Optional<String> m) {
        System.out.println("Carta giocata:" + c.toString());
        this.play = new PlayImpl(c, m);
    }

    /**
     * 
     * @return the Play made by the user.
     */
    public Play getPlay() {
        return this.play;
    }
}
