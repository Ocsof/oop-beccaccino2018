package controller.game;

import java.util.Map;
import java.util.Optional;

import model.entities.AI;
import model.entities.Play;
import model.entities.Player;
import model.logic.Game;
import model.logic.Ruleset;
import view.GameView;

public class GameController {

    public GameController(final Map<Player, Optional<AI>> playingEntities, final Game game, final GameView view) {
        while (!game.isOver()) {
            final Player currentPlayer = game.getCurrentPlayer();
            final Optional<AI> ai = playingEntities.get(currentPlayer);
            if (ai.isPresent()) {
                if (!game.getBriscola().isPresent()) {
                    game.setBriscola(ai.get().selectBriscola());
                }
                final Play play = ai.get().makePlay(game.getCurrentRound());
                game.makeTurn(play);
                view.renderPlay();
            } else {
                if (!game.getBriscola().isPresent()) {
                    game.setBriscola(view.getSelectedBriscola());
                }
                game.makeTurn(view.getUserPlay());
            }
        }
    }
}
