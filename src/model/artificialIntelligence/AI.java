package model.artificialIntelligence;

import model.entities.ItalianCard.Suit;
import model.entities.Play;
import model.logic.Round;

/**
 * An AI making decisions for non-human players.
 */
public interface AI {
    /**
     * Give the AI the opportunity to make its move.
     * 
     * @param currentRound - list of the plays made since this AI last turn,
     * chronologically ordered
     * @return the play this AI decides to make
     */
    Play makePlay(Round currentRound);

    /**
     * Ask the AI which Suit it prefers.
     * 
     * @return the suit selected by the AI
     */
    Suit selectBriscola();
}
