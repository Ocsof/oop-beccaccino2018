package model.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * A generic hand, capable of holding a number of cards depending on extending classes.
 */
public abstract class HandTemplate implements Hand {
    private final List<ItalianCard> cards;

    /**
     * Create an empty hand.
     */
    public HandTemplate() {
        this.cards = new ArrayList<>();
    }

    /**
     * @return a defensive copy of cards held by this hand.
     */
    public List<ItalianCard> getCards() {
        final List<ItalianCard> defensiveCopy = new ArrayList<>(this.cards);
        return defensiveCopy;
    }

    /**
     * {@inheritDoc}
     */
    public void removeCard(final ItalianCard card) {
        this.cards.remove(card);
    }

    /**
     * {@inheritDoc}
     */
    public void addCard(final ItalianCard card) {
        if (!this.isFull()) {
            this.cards.add(card);
        }
    }
}