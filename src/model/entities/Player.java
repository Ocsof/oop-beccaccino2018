package model.entities;

import java.util.Optional;

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
	
	/**
	 * @return an optional containing the AI controlling this player, or an empty
	 *         optional if this player is meant to be user-controlled
	 */
	public Optional<AI> getAI();
	
	/**
	 * @return true if this player is AI driven, false otherwise
	 */
	public default boolean isDrivenByAI() {
		return this.getAI().isPresent();
	};
}
