package model.logic;

import java.util.List;

import model.entities.Player;

public class BeccaccinoTurnOrder implements TurnOrder {
	private final List<Player> players;
	private int index;
	private int counter;

	public BeccaccinoTurnOrder(List<Player> players) {
		this.players = players;
		this.index = 0;
		this.counter = 0;
	}

	@Override
	public Player next() {
		if (this.index == this.players.size() - 1) {
			this.index = 0;
		} else {
			this.index++;
		}
		this.counter++;
		return this.players.get(index);
	}

	@Override
	public void setNext(Player player) {
		this.index = this.players.indexOf(player);
	}

	@Override
	public boolean isOver() {
		return this.counter == this.players.size();
	}

}
