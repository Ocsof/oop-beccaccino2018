package model.entities;

/**
 * A deck made of italian cards
 */
public interface ItalianCardsDeck {
	/**
	 * @return the top card, removing it from the deck
	 */
	public ItalianCard drawCard();
	
	/**
	 * @return the number of remaining cards
	 */
	public int remainingCards();
}
