package model.entities;

/**
 * Implementation of the ItalianCard Interface.
 */
public class ItalianCardImpl implements ItalianCard {
    private final Suit cardSuit;
    private final Value cardValue;

    /**
     * Creates an instance of an ItalianCard.
     * 
     * @param suit - the suit of the card to be created.
     * @param value - the value of the card to be created.
     */
    public ItalianCardImpl(final Suit suit, final Value value) {
        this.cardSuit = suit;
        this.cardValue = value;
    }

    /**
     * {@inheritDoc}
     */
    public Suit getSuit() {
        return this.cardSuit;
    }

    /**
     * {@inheritDoc}
     */
    public Value getValue() {
        return this.cardValue;
    }

    /**
     * {@inheritDoc}
     */
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((cardSuit == null) ? 0 : cardSuit.hashCode());
        result = prime * result + ((cardValue == null) ? 0 : cardValue.hashCode());
        return result;
    }

    /**
     * {@inheritDoc}
     */
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ItalianCardImpl other = (ItalianCardImpl) obj;
        if (cardSuit != other.cardSuit) {
            return false;
        }
        if (cardValue != other.cardValue) {
            return false;
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    public String toString() {
        return this.cardValue + "di" + this.cardSuit;
    }

}
