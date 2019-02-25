package model.entities;

/**
 * A user playing the game
 */
public interface Player {
	/**
	 * @return this player nickname
	 */
	public String getName();

	/**
	 * @return this player hand
	 */
	public Hand getHand();
}
