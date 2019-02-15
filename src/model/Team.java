package model;

import java.util.List;

/**
 * A team competing in the game
 */
public interface Team {
	/**
	 * @return a list of this team players. The order depends on the implementing
	 *         class
	 */
	public List<Player> getPlayers();

	/**
	 * Add a card to the cards won by this team
	 * 
	 * @param won card
	 */
	public void addWonCard(ItalianCard card);

	/**
	 * @return a list of cards won by this team at the current moment. The order is
	 *         chronological
	 */
	public List<ItalianCard> getWonCards();

	/**
	 * Assign extra points to this team
	 * 
	 * @param points to be assigned
	 */
	public void assignExtraPoints(int points);

	/**
	 * @return the extra points assigned to this team
	 */
	public int getExtraPoints();

}
