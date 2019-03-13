package model.logic;

import java.util.ArrayList;
import java.util.List;

import model.entities.ItalianCard;
import model.entities.Play;
import model.entities.Player;

public abstract class RoundTemplate implements Round {
	private final TurnOrder turnOrder;
	private final List<Play> plays;
	private Player currentPlayer;
	
	public RoundTemplate(final TurnOrder turnOrder) {
		this.turnOrder = turnOrder;
		this.plays = new ArrayList<>();
		this.currentPlayer = this.turnOrder.next();
	}

	public Player getCurrentPlayer() {
		this.checkIfOver(false);
		return this.currentPlayer;
	}

	public void addPlay(Play play) {
		this.checkIfOver(false);
		this.plays.add(play);
		this.currentPlayer = this.turnOrder.next();
	}

	public List<Play> getPlays() {
		return this.plays;
	}
	
	protected void checkIfOver(boolean state) {
		if (this.isOver() != state) {
			throw new IllegalStateException();
		}
	}
}
