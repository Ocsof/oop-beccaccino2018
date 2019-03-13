package model.entities;

import java.util.List;

/**
 * An hand capable of holding several italian cards.
 */
public interface Hand {
    /**
     * @return a list of the cards this hand is currently holding. The order
     * depends on the implementing class
     */
    List<ItalianCard> getCards();

    /**
     * It removes the given card, if present.
     * 
     * @param card to be removed
     * @return true if the card has been successfully removed, false otherwise
     */
    boolean removeCard(ItalianCard card);

    /**
     * It adds the given card to the hand.
     * 
     * @param card to be added
     * @return true if the card has been successfully added, false otherwise
     */
    boolean addCard(ItalianCard card);

    /**
     * @return true if the hand is empty, false otherwise
     */
    default boolean isEmpty() {
        return this.getCards().size() == 0;
    }
}
