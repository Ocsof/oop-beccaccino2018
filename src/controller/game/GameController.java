package controller.game;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import model.artificialIntelligence.AI;
import model.entities.Play;
import model.entities.Player;
import model.logic.Game;
import view.GameView;

public class GameController {
    private final Map<Player, Optional<AI>> playingEntities;
    private final Game game;
    private final GameView view;

    public GameController(final Map<Player, Optional<AI>> playingEntities, final Game game, final GameView view) {
        this.view = view;
        this.game = game;
        this.playingEntities = playingEntities;
        this.handleBriscola();
        this.proceed(null);
    }

    public void notifyUserHasPlayed(final Play play) {
        this.proceed(play);
    }

    private void proceed(final Play play) {
        if (!this.game.isOver()) {
            final Player currentPlayer = this.game.getCurrentPlayer();
            final Optional<AI> ai = this.playingEntities.get(currentPlayer);
            if (ai.isPresent()) {
                this.game.makeTurn(ai.get().makePlay(this.game.getCurrentRound()));
                view.update();
                this.proceed(null);
            } else {
                if (play == null) {
                    this.view.allowUserPlay();
                } else {
                    game.makeTurn(play);
                    view.update();
                }
            }
        }
    }

    private void handleBriscola() {
        final Player currentPlayer = this.game.getCurrentPlayer();
        final Optional<AI> ai = this.playingEntities.get(currentPlayer);
        if (ai.isPresent()) {
            if (!this.game.getBriscola().isPresent()) {
                this.game.setBriscola(ai.get().selectBriscola());
            }
        } else {
            this.game.setBriscola(view.getSelectedBriscola());
        }
        this.updateAI();
    }

    private Set<AI> getAllAI() {
        final Set<AI> allAI = new HashSet<>();
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
