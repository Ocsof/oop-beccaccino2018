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

/**
 * A simple controller that makes playing entities interact with the game.
 */
public class GameController {
    private final Map<Player, Optional<AI>> playingEntities;
    private final Game game;
    private final GameView view;

    /**
     * @param playingEntities - an optional AI should be associated to each
     * player. If the optional is empty, that means the player is meant to be
     * controlled via user interface.
     * @param game - the game
     * @param view - the game view
     */
    public GameController(final Map<Player, Optional<AI>> playingEntities, final Game game, final GameView view) {
        this.view = view;
        this.game = game;
        this.playingEntities = playingEntities;
        this.handleBriscola();
        this.proceed(null);
    }

    /**
     * Notify this controller that the user has selected a play: the game can proceed.
     * 
     * @param play - the play made by the user
     */
    public void notifyUserHasPlayed(final Play play) {
        this.proceed(play);
    }

    /**
     * If not over, pass the game a play made either by the AIs or by the user.
     * 
     * @param play - the play made by the user. If this argument is null then
     * the controller will ask the view for a play.
     */
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
                    this.proceed(null);
                }
            }
        }
    }

    /**
     * This routine makes the AI associated to the first player select a
     * briscola and passes it to the game. If the first player is associated to
     * the user, ask the view for the briscola suit.
     */
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

    /**
     * Utility method extrapolating AIs from the playing entities map.
     * 
     * @return all AI playing the game
     */
    private Set<AI> getAllAI() {
        final Set<AI> allAI = new HashSet<>();
        for (Optional<AI> ai : this.playingEntities.values()) {
            if (ai.isPresent()) {
                allAI.add(ai.get());
            }
        }
        return allAI;
    }

    /**
     * Notify all AIs which briscola has been selected.
     */
    private void updateAI() {
        for (AI intelligence : this.getAllAI()) {
            intelligence.setBriscola(game.getBriscola().get());
        }
    }
}
