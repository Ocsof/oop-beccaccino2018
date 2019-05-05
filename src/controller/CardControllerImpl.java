package controller;

import java.util.Optional;

import model.entities.ItalianCard;
import model.entities.Play;
import model.entities.PlayImpl;

/**
 * Alessia Rocco 
 * CardController implementation, contains the controller of the cardView.
 */
public class CardControllerImpl implements CardController {
    private Play play;

    /**
     * {@inheritDoc}
     */
    public void action(final ItalianCard c, final Optional<String> m) {
        System.out.println("Carta giocata:" + c.toString());
        this.play = new PlayImpl(c, m);
    }

    /**
     * {@inheritDoc}
     */
    public Play getPlay() {
        return this.play;
    }
}
