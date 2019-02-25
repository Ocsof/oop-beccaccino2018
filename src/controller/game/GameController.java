package controller.game;

import java.util.Map;
import java.util.Optional;

import model.entities.AI;
import model.entities.Play;
import model.entities.Player;
import model.logic.Match;
import model.logic.Ruleset;
import view.GameView;

public class GameController {

	public GameController(Ruleset ruleset) {
		final Match game = ruleset.startNewMatch();
		final GameView view = ruleset.createGameView(game);
		final Map<Player, Optional<AI>> playingEntities = ruleset.getPlayingEntities(game);

		while (!game.isOver()) {
			final Player nextPlayer = game.getNextPlayer();
			final Optional<AI> ai = playingEntities.get(nextPlayer);
			if (ai.isPresent()) {
				final Play play = ai.get().makePlay(game.getCurrentRound());
				game.makeTurn(play);
				view.renderAIPlay(nextPlayer, play);
			} else {
				game.makeTurn(view.getUserPlay(nextPlayer));
			}
		}
	}

}
