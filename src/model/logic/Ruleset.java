package model.logic;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import model.entities.AI;
import model.entities.Player;
import view.GameView;

public interface Ruleset {
    Game newGame(List<Player> players);

    GameView newGameView(Game game);
    
    Player newPlayer(String name);
}
