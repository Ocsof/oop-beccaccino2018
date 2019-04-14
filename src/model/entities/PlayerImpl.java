package model.entities;

/**
 * Alessia Rocco
 * Player Implementation.
 */
public class PlayerImpl implements Player {
    private String name;
    private Hand hand;
    /**
     * Class constructor.
     * @param name name of the player
     * @param hand hand of the player
     */
    public PlayerImpl(final String name, final Hand hand) {
        this.name = name;
        this.hand = hand;
    }
    /**
     * {@inheritDoc}
     */
    public String getName() {
        return this.name;
    }
    /**
     * {@inheritDoc}
     */
    public Hand getHand() {
        return this.hand;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((hand == null) ? 0 : hand.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }
    @Override
    public boolean equals(final Object player) {
        if (this == player) {
            return true;
        }
        if (player == null) {
            return false;
        }
        if (getClass() != player.getClass()) {
            return false;
        }
        PlayerImpl other = (PlayerImpl) player;
        if (hand == null) {
            if (other.hand != null) {
                return false;
            }
        } else if (!hand.equals(other.hand)) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }
}
