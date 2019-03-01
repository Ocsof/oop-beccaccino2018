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
		boolean briscolaSelected = false;
		
		while (!game.isOver()) {
			final Player currentPlayer = game.getCurrentPlayer();
			final Optional<AI> ai = playingEntities.get(currentPlayer);
			if (ai.isPresent()) {
				if(!briscolaSelected) {
					game.setBriscola(ai.get().selectBriscola());
					briscolaSelected = true;
				}
				final Play play = ai.get().makePlay(game.getCurrentRound());
				game.makeTurn(play);
				view.renderAIPlay(currentPlayer, play);
			} else {
				if(!briscolaSelected) {
					game.setBriscola(view.getSelectedBriscola(currentPlayer));
					briscolaSelected = true;
				}
				game.makeTurn(view.getUserPlay(currentPlayer));
			}
		}
	}
}
