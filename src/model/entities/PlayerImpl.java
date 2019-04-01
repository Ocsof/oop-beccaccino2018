package model.entities;
/**
 * Alessia Rocco
 * Player Implementation.
 */
public class PlayerImpl implements Player {
    private String name;
    private Hand hand;

    public PlayerImpl(final String name, final Hand hand) {
        super();
        this.name = name;
        this.hand = hand;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Hand getHand() {
        return this.hand;
    }
}
