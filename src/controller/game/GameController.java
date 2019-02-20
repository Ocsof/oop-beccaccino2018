package controller.game;

import model.entities.Play;
import model.entities.Player;
import model.logic.Game;
import model.logic.Ruleset;
import view.GameView;

public class GameController {

	public GameController(Ruleset ruleset) {
		final Game game = ruleset.createNewGame();
		final GameView view = ruleset.createGameView(game);

		while (!game.isOver()) {
			final Player nextPlayer = game.getNextPlayer();
			if (nextPlayer.isDrivenByAI()) {
				final Play play = nextPlayer.getAI().get().makePlay(game.getThisRoundPlays());
				game.makeTurn(play);
				view.renderAIPlay(nextPlayer, play);
			} else {
				game.makeTurn(view.getUserPlay(nextPlayer));
			}
		}
	}

}
