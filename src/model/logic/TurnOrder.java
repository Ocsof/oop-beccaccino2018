package model.logic;

import model.entities.Player;

public interface TurnOrder {
	public Player next();
	public void setNext(Player player);
	public boolean isOver();
}
