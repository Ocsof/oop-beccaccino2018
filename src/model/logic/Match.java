package model.logic;


import java.util.List;

import model.entities.Play;
import model.entities.Player;
import model.entities.Team;

/**
 * A game that needs input from the outside to proceed through every turn
 */
public interface Match {
	/**
	 * @return a list of this match players, in turn
	 */
	public List<Player> getPlayers();

	/**
	 * @return a list of the teams in this match
	 */
	public List<Team> getTeams();

	/**
	 * @return a list of all the plays already made this match, chronologically
	 *         ordered
	 */
	public List<Play> getAllPlays();

	/**
	 * @return the next turn player
	 */
	public Player getNextPlayer();

	/**
	 * This method is the only way to proceed through the match
	 * 
	 * @param the play that should be made
	 */
	public void makeTurn(Play play);

	/**
	 * @return true if the match is over, false otherwise
	 */
	public boolean isOver();
}

/*
private void setPlayers(List<Player> players);
public void dealCards();
public void setTeams();
*/
