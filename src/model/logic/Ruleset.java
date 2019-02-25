package model.logic;

import java.util.Map;
import java.util.Optional;

import model.entities.AI;
import model.entities.Player;
import view.GameView;

	
public interface Ruleset {
	public Match startNewMatch();
	public GameView createGameView(Match game);
	public Map<Player, Optional<AI>> getPlayingEntities(Match game);
}
