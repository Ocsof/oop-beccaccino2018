package model.logic;


import java.util.List;
import java.util.Optional;

import model.entities.ItalianCard.Suit;
import model.entities.Play;
import model.entities.Player;
import model.entities.Team;

/**
 * A game that needs input from the outside to proceed through every turn
 */
public interface Match {
	/**
	 * @return a list of this match players
	 */
	public List<Player> getPlayers();

	/**
	 * @return a list of the teams in this match
	 */
	public List<Team> getTeams();

	/**
	 * @return the current round
	 */
	public Round getCurrentRound();

	/**
	 * @return the player on the play
	 */
	public Player getCurrentPlayer();
	
	/**
	 * Set this match briscola
	 * 
	 * @param the suit to be set as briscola
	 */
	public void setBriscola(Suit briscola);
	
	/**
	 * Get this match briscola suit, if present
	 * 
	 * @param an optional containing this briscola suit, or an empty optional if
	 *           there isn't a briscola
	 */
	public Optional<Suit> getBriscola();

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
