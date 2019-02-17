package model.logic;

import java.util.Optional;

import model.entities.ItalianCard;

public interface RoundCalculator {
	/**
	 * Add the last card played this round
	 * 
	 * @param played card
	 * @return true if the card has been successfully added, false otherwise
	 */
	public boolean addPlayedCard(ItalianCard card);

	/**
	 * @return true if the round is over, false otherwise
	 */
	public boolean isRoundOver();

	/**
	 * @return an optional containing the winning card of the round. Return an empty
	 *         optional otherwise
	 */
	public Optional<ItalianCard> getWinningCard();
}
