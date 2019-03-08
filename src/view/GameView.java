package view;

import model.entities.ItalianCard.Suit;
import model.entities.Play;
import model.entities.Player;

/**
 * A GUI for a turn based italian card game
 */
public interface GameView {
	/**
	 * @param the player that needs user to make his play
	 * @return the play made by the user
	 */
	public Play getUserPlay();
	
	/**
	 * @param the player that has to select the briscola suit
	 * @return the suit selected by the user
	 */
	public Suit getSelectedBriscola();

	/**
	 * @param the player whose play has already been made by the AI
	 * @param the play made by the AI
	 */
	public void renderPlay();
}

/*
 * LA CLASSE CHE IMPLEMENTA QUESTA INTERFACCIA PRENDE DA COSTRUTTORE UN MATCH
 */