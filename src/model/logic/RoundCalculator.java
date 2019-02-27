package model.logic;

import java.util.List;

import model.entities.ItalianCard.Suit;
import model.entities.Play;

public interface RoundCalculator {
	public Play getWinningPlay(List<Play> plays, Suit roundSuit);
}
