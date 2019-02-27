package model.logic;

import java.util.ArrayList;
import java.util.List;

import model.entities.ItalianCard.Suit;
import model.entities.ItalianCardsDeck;
import model.entities.Play;
import model.entities.Player;
import model.entities.Team;

public abstract class BeccaccinoMatch implements Match {
	private static Suit briscola;
	private final List<Player> players;
	private final List<Team> teams;


	public BeccaccinoMatch(List<Player> players) {
		this.players = players;
		this.teams = new ArrayList<>();
		ItalianCardsDeck replace = null;
		this.dealCards(replace);
	}
	
	public static Suit getBriscola() {
		return BeccaccinoMatch.briscola;
	}

	@Override
	public List<Player> getPlayers() {
		return this.players;
	}

	@Override
	public List<Team> getTeams() {
		return this.teams;
	}

	@Override
	public Round getCurrentRound() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Player getCurrentPlayer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void makeTurn(Play play) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean isOver() {
		for (Player player : players) {
			if (!player.getHand().isEmpty()) {
				return false;
			}
		}
		return true;
	}
	
	public abstract void dealCards(ItalianCardsDeck deck);
	

}
