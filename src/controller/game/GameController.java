package controller.game;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import model.artificialIntelligence.AI;
import model.entities.Play;
import model.entities.Player;
import model.logic.Game;
import view.GameView;

public class GameController {
    private final Map<Player, Optional<AI>> playingEntities;
    private final Game game;

    public GameController(final Map<Player, Optional<AI>> playingEntities, final Game game, final GameView view) {
        this.game = game;
        this.playingEntities = playingEntities;
        while (!game.isOver()) {
            final Player currentPlayer = game.getCurrentPlayer();
            final Optional<AI> ai = playingEntities.get(currentPlayer);
            if (ai.isPresent()) {
                if (!game.getBriscola().isPresent()) {
                    game.setBriscola(ai.get().selectBriscola());
                    this.updateAI();
                }
                final Play play = ai.get().makePlay(game.getCurrentRound());
                game.makeTurn(play);
                view.renderPlay();
            } else {
                if (!game.getBriscola().isPresent()) {
                    game.setBriscola(view.getSelectedBriscola());
                    this.updateAI();
                }
                game.makeTurn(view.getUserPlay());
            }
        }
    }

    private Set<AI> getAllAI() {
        final Set<AI> allAI = new TreeSet<>();
        for (Optional<AI> ai : this.playingEntities.values()) {
            if (ai.isPresent()) {
                allAI.add(ai.get());
            }
        }
        return allAI;
    }

    private void updateAI() {
        for (AI intelligence : this.getAllAI()) {
            intelligence.setBriscola(game.getBriscola().get());
        }
    }
}
