package model.logic;

import java.util.HashMap;
import java.util.Map;

import model.entities.ItalianCard.Suit;
import model.entities.Play;
import model.entities.Player;
import model.entities.Round;

public abstract class RoundTemplate implements Round {
	private final TurnOrder turnOrder;
	private final Map<Player, Play> plays;
	private Player nextPlayer;
	private Suit suit;
	

	public RoundTemplate(final TurnOrder turnOrder) {
		this.turnOrder = turnOrder;
		this.plays = new HashMap<>();
	}

	public boolean isOver() {
		return this.turnOrder.isOver();
	}

	public Player getNextPlayer() {
		return this.nextPlayer;
	}

	public abstract Player getWinningPlayer();

	public Suit getSuit() {
		return this.suit;
	}

	public void addPlay(Play play) {
		if(this.plays.size() == 0) {
			this.suit = play.getCard().getSuit();
		}
		this.plays.put(this.turnOrder.next(), play);
	}

	public Play getPlay(Player player) {
		return this.plays.get(player);
	}

}
