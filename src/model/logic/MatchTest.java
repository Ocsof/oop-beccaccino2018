package model.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.artificialIntelligence.AI;
import model.entities.Play;
import model.entities.Player;

/**
 * JUnit test for class implementing Game.
 */
public class MatchTest {
    public static void main(final String[] args) {
        final Ruleset ruleset = new RulesetImpl();

        List<Player> playerList = new ArrayList<Player>();
        playerList.add(ruleset.newPlayer("Player1"));
        playerList.add(ruleset.newPlayer("Player2"));
        playerList.add(ruleset.newPlayer("Player3"));
        playerList.add(ruleset.newPlayer("Player4"));
        Game game = ruleset.newGame(playerList);
        Map<Player, AI> playingEntities = new HashMap<>();
        playingEntities.put(playerList.get(0), ruleset.newAI(playerList.get(0)).get());
        playingEntities.put(playerList.get(1), ruleset.newAI(playerList.get(1)).get());
        playingEntities.put(playerList.get(2), ruleset.newAI(playerList.get(2)).get());
        playingEntities.put(playerList.get(3), ruleset.newAI(playerList.get(3)).get());
        while (!game.isOver()) {
            final Player currentPlayer = game.getCurrentPlayer();
            final AI ai = playingEntities.get(currentPlayer);
            if (!game.getBriscola().isPresent()) {
                game.setBriscola(ai.selectBriscola());
                for (AI intelligence : playingEntities.values()) {
                    intelligence.setBriscola(game.getBriscola().get());
                }
            }
            final Play play = ai.makePlay(game.getCurrentRound());
            game.makeTurn(play);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
