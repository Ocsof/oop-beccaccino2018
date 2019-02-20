package model.entities;

import java.util.List;

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
	public Play makePlay(List<Play> lastPlays);
}
