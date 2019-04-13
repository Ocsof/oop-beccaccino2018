package model.entities;

/**
 * A generic hand, capable of holding a maximum number of {@value #MAX_HAND_SIZE} cards.
 */
public class BeccaccinoHand extends HandTemplate {
    private static final int MAX_HAND_SIZE = 10;

    /**
     * Create an empty hand.
     */
    public BeccaccinoHand() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    public boolean isFull() {
        if (this.getCards().size() == MAX_HAND_SIZE) {
            return true;
        }
        return false;
    }

}
