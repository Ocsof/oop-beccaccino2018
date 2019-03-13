package model.logic;

import java.util.Map;
import java.util.Optional;

import model.entities.AI;
import model.entities.Player;
import view.GameView;

public interface Ruleset {
    Match startNewMatch();

    GameView createGameView(Match game);

    Map<Player, Optional<AI>> getPlayingEntities(Match game);
}
