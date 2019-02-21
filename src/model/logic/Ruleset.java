package model.logic;

import view.GameView;

public interface Ruleset {
	public Match createNewMatch();
	public GameView createGameView(Match game);
}
