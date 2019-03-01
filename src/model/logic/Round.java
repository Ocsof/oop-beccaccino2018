package model.logic;

import model.entities.Play;
import model.entities.Player;

import java.util.List;

import model.entities.ItalianCard.Suit;

public interface Round {
	/**
	 * Adds a play to this round, thus passing the turn to the next player (if the
	 * round isn't finished yet)
	 * 
	 * @param play - the play to be added to this round
	 */
	public void addPlay(Play play); // TODO add exception

	/**
	 * Returns the player on the play, only if the round isn't finished yet
	 * 
	 * @return the player on the play
	 */
	public Player getCurrentPlayer();  //TODO add exception
	
	/**
	 * Returns this round plays
	 * 
	 * @return a list of this round plays, chronologically ordered
	 */
	public List<Play> getPlays();
	
	/**
	 * Checks whether this round is over or not
	 * 
	 * @return true if this round is over, false otherwise
	 */
	public boolean isOver();
	
	/**
	 * Returns the winning play of this round
	 * 
	 * @return the winning play if this round is over
	 */
	public Play getWinningPlay();  //TODO add exception
}
