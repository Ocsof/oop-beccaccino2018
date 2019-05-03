package tests;

import java.util.ArrayList;
import java.util.List;


import model.artificialIntelligence.AI;
import model.entities.BeccaccinoBunchOfCards;
import model.entities.BunchOfCards;
import model.entities.ItalianCard.Suit;
import model.logic.Game;
import model.logic.Ruleset;
import model.logic.RulesetImpl;
import model.entities.Play;
import model.entities.Player;

/**
 * JUnit test for class implementing Game.
 */
public final class MatchTest {
    private MatchTest() {
    }
    private static final int TIME_BETWEEN_PLAYS_IN_MS = 1000;
    /**
     * Launching this main will simulate a game between AIs.
     * @param args - args
     */
    public static void main(final String[] args) {
        final Ruleset ruleset = new RulesetImpl();

        List<Player> playerList = new ArrayList<Player>();
        playerList.add(ruleset.newPlayer("Player1"));
        playerList.add(ruleset.newPlayer("Player2"));
        playerList.add(ruleset.newPlayer("Player3"));
        playerList.add(ruleset.newPlayer("Player4"));
        Game game = ruleset.newGame(playerList);
        List<AI> ais = new ArrayList<>();
        ais.add(ruleset.newAI(playerList.get(0), "Basic AI").get());
        ais.add(ruleset.newAI(playerList.get(1), "Basic AI").get());
        ais.add(ruleset.newAI(playerList.get(2), "Basic AI").get());
        ais.add(ruleset.newAI(playerList.get(3), "Basic AI").get());

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
            System.out.println("-------------------------------------------");
            System.out.println(currentPlayer);
            System.out.println(game.getCurrentRound().getPlayableCards());
            final BunchOfCards bunchOfCards = new BeccaccinoBunchOfCards(game.getCurrentRound().getPlayableCards());
            System.out.println("CardsOfSuit: " + bunchOfCards.getCardsOfSuit(Suit.BASTONI));
            System.out.println("LeastPointCards: " + bunchOfCards.getCardsWithLeastPoints());
            System.out.println("MostPointCards: " + bunchOfCards.getCardsWithMostPoints());
            System.out.println("HighestCards: " + bunchOfCards.getHighestCards());
            System.out.println("LowestCards: " + bunchOfCards.getLowestCards());
            final Play play = ai.makePlay(game.getCurrentRound());
            System.out.println("PLAY:" + play.toString());
            game.makeTurn(play);
            try {
                Thread.sleep(TIME_BETWEEN_PLAYS_IN_MS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
