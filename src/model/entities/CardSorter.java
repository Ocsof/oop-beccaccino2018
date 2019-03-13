package model.entities;

import java.util.List;

import model.entities.ItalianCard.Suit;
import model.entities.ItalianCard.Value;

/** A simple card container with utility methods.
 */
public interface CardSorter {
    /**
     * Returns cards contained of specified suit.
     * 
     * @param suit - the suit you want to get cards of
     * @return a list of cards of specified suit, ordered by value 
     */
    List<ItalianCard> getCardsOfSuit(Suit suit);

    /**
     * Returns cards contained of specified value.
     * 
     * @param value - the value you want to get cards of
     * @return a list of cards of specified value 
     */
    List<ItalianCard> getCardsOfValue(Value value);
}
