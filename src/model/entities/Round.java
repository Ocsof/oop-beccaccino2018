package model.entities;

import model.entities.ItalianCard.Suit;

public interface Round {
	public boolean isOver();
	public Player getNextPlayer();
	public Player getWinningPlayer();
	public Suit getSuit();
	public void addPlay(Play play);
	public Play getPlay(Player player);
}
