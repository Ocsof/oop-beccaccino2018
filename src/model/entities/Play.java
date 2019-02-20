package model.entities;

import java.util.Optional;

/**
 * A play is made by a card along with an optional message
 */
public interface Play {
	/**
	 * @return the card played
	 */
	public ItalianCard getCard();

	/**
	 * @return an optional containing the message sent, or an empty optional if no
	 *         message should be sent
	 */
	public Optional<String> getMessage();
}
