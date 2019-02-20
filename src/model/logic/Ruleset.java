package model.logic;

import view.GameView;

public interface Ruleset {
	public Game createNewGame();
	public GameView createGameView(Game game);
}
