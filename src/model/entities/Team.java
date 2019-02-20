package model.entities;

import java.util.List;

/**
 * A team competing in the game
 */
public interface Team {
	/**
	 * @param player to be added
	 * @return true if the player has been successfully added, false otherwise
	 */
	public boolean addPlayer(Player player);
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
	 * @return extra points currently assigned to this team
	 */
	public int getExtraPoints();

}
