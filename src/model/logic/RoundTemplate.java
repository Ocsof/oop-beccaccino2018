package model.logic;

import java.util.ArrayList;
import java.util.List;


import model.entities.ItalianCard.Suit;
import model.entities.Play;
import model.entities.Player;

public abstract class RoundTemplate implements Round {
	private final TurnOrder turnOrder;
	private final List<Play> plays;
	private final List<Player> players;
	private Player currentPlayer;
	
	
	
	public RoundTemplate(final TurnOrder turnOrder) {
		this.turnOrder = turnOrder;
		this.plays = new ArrayList<>();
		this.players = new ArrayList<>();
		this.currentPlayer = this.turnOrder.next();
	}

	public abstract boolean isOver();

	public Player getCurrentPlayer() {
		return this.currentPlayer;
	}

	public abstract Play getWinningPlay();

	public void addPlay(Play play) {
		this.plays.add(play);
		this.players.add(currentPlayer);
		this.currentPlayer = this.turnOrder.next();
	}

	public List<Play> getPlays() {
		return this.plays;
	}

}
