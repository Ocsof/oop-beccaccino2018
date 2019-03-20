package model.logic;

import java.util.ArrayList;
import java.util.List;
import model.entities.Play;
import model.entities.Player;

/**
 * This models the abstract concept of round.
 */
public abstract class RoundTemplate implements Round {
    private final TurnOrder turnOrder;
    private final List<Play> plays;
    private Player currentPlayer;

    /**
     * @param turnOrder - the turn order this round should follow.
     */
    public RoundTemplate(final TurnOrder turnOrder) {
        this.turnOrder = turnOrder;
        this.plays = new ArrayList<>();
        this.currentPlayer = this.turnOrder.next();
    }

    /**
     * {@inheritDoc}
     */
    public Player getCurrentPlayer() {
        this.checkIfOver(false);
        return this.currentPlayer;
    }

    /**
     * {@inheritDoc}
     */
    public void addPlay(final Play play) {
        this.checkIfOver(false);
        this.plays.add(play);
        this.currentPlayer = this.turnOrder.next();
    }

    /**
     * {@inheritDoc}
     */
    public List<Play> getPlays() {
        return this.plays;
    }

    /**
     * This is a protection method checking the state of this round.
     * 
     * @param state - boolean representing what the {@link isOver()} method
     * should return.
     * @throws an IllegalStateException if this round state isn't how it was
     * expected to be.
     */
    protected void checkIfOver(final boolean state) {
        if (state != this.isOver()) {
            throw new IllegalStateException();
        }
    }
}
