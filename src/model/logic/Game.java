package model.logic;


import java.util.List;

import model.entities.ItalianCard;
import model.entities.Play;
import model.entities.Player;
import model.entities.Team;

public interface Game {
	public List<Player> getPlayers();
	public List<Team> getTeams();
	public List<Play> getThisRoundPlays();
	public List<ItalianCard> getCardsPlayed();
	public Player getNextPlayer();
	public void makeTurn(Play play);
	public boolean isOver();
}


/*
private void setPlayers(List<Player> players);
public void dealCards();
public void setTeams();
*/
