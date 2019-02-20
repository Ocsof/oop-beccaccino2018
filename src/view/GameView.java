package view;

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
	public Play getUserPlay(Player player);

	/**
	 * @param the player whose play has already been made by the AI
	 * @param the play made by the AI
	 */
	public void renderAIPlay(Player player, Play play);
}

/*
 * LA CLASSE CHE IMPLEMENTA QUESTA INTERFACCIA PRENDE DA COSTRUTTORE UNA
 * List<Player> CON TUTTI I GIOCATORI
 */