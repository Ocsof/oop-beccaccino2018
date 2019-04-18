package model.logic;

import java.util.ArrayList;
import java.util.List;

import model.entities.Player;

/**
 * {@inheritDoc}.
 */
public class BasicTurnOrder implements TurnOrder {
    private final List<Player> players;
    private int index;

    /**
     * 
     * @param players - a list of players. The sorting of the list determines
     * the turn order.
     */
    public BasicTurnOrder(final List<Player> players) {
        this.players = new ArrayList<>(players);
        this.index = 0;
    }

    /**
     * {@inheritDoc}
     */
    public Player next() {
        final Player nextPlayer = this.players.get(index);
        if (this.index == this.players.size() - 1) {
            this.index = 0;
        } else {
            this.index++;
        }
        return nextPlayer;
    }

    /**
     * {@inheritDoc}
     */
    public void setNext(final Player player) {
        this.index = this.players.indexOf(player);
    }

    /**
     * {@inheritDoc}
     */
    public List<Player> getPlayers() {
        final List<Player> defensiveCopy = new ArrayList<>(this.players);
        return defensiveCopy;
    }
}
