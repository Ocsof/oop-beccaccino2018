package model.logic;

import model.entities.Player;

/**
 * A circular iterator over given players.
 */
public interface TurnOrder {
    /**
     * Returns the next player in the iteration.
     * 
     * @return the next player
     */
    Player next();

    /**
     * Set the given player as next.
     * 
     * @param player - the player to be set as next
     */
    void setNext(Player player);
}
