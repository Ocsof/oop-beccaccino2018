package controller.game;

import model.entities.Play;
import model.entities.Player;
import model.logic.Match;
import model.logic.Ruleset;
import view.GameView;

public class GameController {

	public GameController(Ruleset ruleset) {
		final Match game = ruleset.createNewMatch();
		final GameView view = ruleset.createGameView(game);

		while (!game.isOver()) {
			final Player nextPlayer = game.getNextPlayer();
			if (nextPlayer.isDrivenByAI()) {
				final Play play = nextPlayer.getAI().get().makePlay(game.getAllPlays());
				game.makeTurn(play);
				view.renderAIPlay(nextPlayer, play);
			} else {
				game.makeTurn(view.getUserPlay(nextPlayer));
			}
		}
	}

}
