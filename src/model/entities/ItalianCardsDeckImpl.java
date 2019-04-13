package model.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import model.entities.ItalianCard;

public class ItalianCardsDeckImpl implements ItalianCardsDeck {
    
    private List<ItalianCardImpl> cardDeck;
    private final int indexFirstCard = 0;
    
    public ItalianCardsDeckImpl() {
        
        cardDeck = new ArrayList<ItalianCardImpl>();
        populateDeck();
        Collections.shuffle(cardDeck);
        
    }

    @Override
    public ItalianCard drawCard() {
        if(remainingCards() == 0) {
            throw new IllegalStateException("Cards cannot be drawn if the deck is empty.");
        }else {
            return cardDeck.remove(indexFirstCard);
        }
    }

    @Override
    public int remainingCards() {
        return cardDeck.size();
    }
    
    /**
     * This private method populates a deck with all 40 possible ItalianCards.
     * @return void
     */
    private void populateDeck() {
        EnumSet<ItalianCard.Suit> suitList = EnumSet.allOf(ItalianCard.Suit.class);
        EnumSet<ItalianCard.Value> valueList = EnumSet.allOf(ItalianCard.Value.class);
        for (ItalianCard.Suit suit : suitList) {
            for (ItalianCard.Value value : valueList) {
                cardDeck.add(new ItalianCardImpl(suit, value));
            }
        }
    }

}
