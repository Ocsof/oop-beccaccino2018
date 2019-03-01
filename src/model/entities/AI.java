package model.entities;

import model.entities.ItalianCard.Suit;
import model.logic.Round;

/**
 * An AI making decisions for non-human players
 */
public interface AI {
	/**
	 * Give the AI the opportunity to make its move
	 * 
	 * @param list of the plays made since this AI last turn, chronologically ordered
	 * @return the play this AI decides to make
	 */
	public Play makePlay(Round currentRound);
	
	/**
	 * Ask the AI which Suit it prefers
	 * 
	 * @return the suit selected by the AI
	 */
	public Suit selectBriscola();
}

//The constructor needs the player
