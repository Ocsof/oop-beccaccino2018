package controller;

import java.util.Optional;

import model.entities.ItalianCard;
import model.entities.Play;

/**
 * Alessia Rocco 
 * An interface to deal with card's actions.
 */
public interface CardController {
    /**
     * the ActionListener add to every playable card.
     * 
     * @param c card clicked
     * @param m message selected if present
     */
    void action(ItalianCard c, Optional<String> m);

    /**
     * 
     * @return the Play made by the user.
     */
    Play getPlay();
}
