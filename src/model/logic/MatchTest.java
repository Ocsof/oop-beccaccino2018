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
        List<AI> ais = new ArrayList<>();
        ais.add(ruleset.newAI(playerList.get(0)).get());
        ais.add(ruleset.newAI(playerList.get(1)).get());
        ais.add(ruleset.newAI(playerList.get(2)).get());
        ais.add(ruleset.newAI(playerList.get(3)).get());
        /**con le mappe NON FUNZIONA
        playingEntities.put(playerList.get(0), ruleset.newAI(playerList.get(0)).get());
        playingEntities.put(playerList.get(1), ruleset.newAI(playerList.get(1)).get());
        playingEntities.put(playerList.get(2), ruleset.newAI(playerList.get(2)).get());
        playingEntities.put(playerList.get(3), ruleset.newAI(playerList.get(3)).get());
        */
        while (!game.isOver()) {
            final Player currentPlayer = game.getCurrentPlayer();
            final AI ai = ais.get(playerList.indexOf(currentPlayer));
            if (!game.getBriscola().isPresent()) {
                game.setBriscola(ai.selectBriscola());
                System.out.println("Briscola: " + game.getBriscola().get());
                for (AI intelligence : ais) {
                    intelligence.setBriscola(game.getBriscola().get());
                }
            }
            System.out.println(game.getCurrentRound().getPlayableCards());
            final Play play = ai.makePlay(game.getCurrentRound());
            System.out.println(play.toString());
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
