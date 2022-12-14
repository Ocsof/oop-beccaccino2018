package model.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.entities.BeccaccinoBunchOfCards;
import model.entities.BunchOfCards;
import model.entities.ItalianCard;
import model.entities.ItalianCard.Suit;
import model.entities.ItalianCard.Value;
import model.entities.ItalianCardImpl;
import model.entities.ItalianCardsDeck;
import model.entities.ItalianCardsDeckImpl;
import model.entities.Play;
import model.entities.Player;
import model.entities.Team;

/**
 * A beccaccino game.
 * @see <a href="Marafone Beccaccino">https://it.wikipedia.org/wiki/Marafone_Beccacino</a>
 */
public class BeccaccinoGame extends GameTemplate {
    private static final int NUMBER_OF_PLAYERS = 4;
    private static final int LAST_ROUND_POINTS = 3;

    private final List<Team> teams;
    private Map<Play, Player> playersByPlay;

    /**
     * Only {@value #NUMBER_OF_PLAYERS} players can play a beccaccino game.
     * 
     * @param turnOrder - the turn order this game should follow
     * @param team1 - first team
     * @param team2 - second team
     */
    public BeccaccinoGame(final TurnOrder turnOrder, final Team team1, final Team team2) {
        super(turnOrder);
        if (turnOrder.getPlayers().size() != NUMBER_OF_PLAYERS) {
            throw new IllegalArgumentException(
                    "Provided turn order has a number of players different from " + NUMBER_OF_PLAYERS);
        }
        this.teams = new ArrayList<>();
        this.teams.add(team1);
        this.teams.add(team2);
        this.playersByPlay = new HashMap<>();
    }

    /**
     * Deal 10 cards to each player.
     * 
     * @param deck - needed cards are taken from here
     */
    protected void dealCards(final ItalianCardsDeck deck) {
        while (deck.remainingCards() >= this.getPlayers().size()) {
            super.dealCards(deck);
        }
    }

    /**
     * {@inheritDoc}
     */
    public List<Team> getTeams() {
        return this.teams;
    }

    /**
     * {@inheritDoc}
     */
    public void makeTurn(final Play play) {
        this.playersByPlay.put(play, this.getCurrentPlayer());
        super.makeTurn(play);
    }

    /**
     * This makes the abstract factory method return an instance of "BeccaccinoRound".
     * 
     * @param turnOrder - the turn order the round should follow.
     * @return a BeccaccinoRound
     */
    protected Round newRound(final TurnOrder turnOrder) {
        final Round newRound = new BeccaccinoRound(turnOrder, this.getBriscola().get());
        return newRound;
    }

    /**
     * When a round is over, following things have to be done, in order:.
     * -the winning player of the round is set as the first player of the following round;
     * -the team of the winning player is given won cards;
     * -if the round is the last one, extra points are added to winning team;
     * -if the game is over, compute and give points to each team according to won cards.
     */
    protected void roundOverRoutine() {
       final Round finishedRound = this.getCurrentRound();
        if (!finishedRound.isOver()) {
            throw new IllegalStateException("This method should be called only when the current round is over");
        }
       final Player winningPlayer = this.playersByPlay.get(finishedRound.getWinningPlay().get());
       final Team winningTeam = this.getTeamOf(winningPlayer);
       this.getTurnOrder().setNext(winningPlayer);
       this.playersByPlay.clear();
       for (ItalianCard card : finishedRound.getPlayedCards()) {
           winningTeam.addWonCard(card);
       }
       if (this.isOver()) {
           winningTeam.assignPoints(LAST_ROUND_POINTS);
           for (Team team : this.teams) {
               final BunchOfCards wonCards = new BeccaccinoBunchOfCards(team.getWonCards());
               team.assignPoints(wonCards.getPoints());
           }
       }
    }

    /**
     * {@inheritDoc} 
     */
    protected void firstTurnRoutine(final Play play) {
    }

    /**
     * {@inheritDoc} 
     * In a Beccaccino game, in order to choose the first player,
     * cards have to be dealt. Then whoever holds the "quattro di denari" goes first.
     */
    protected Player selectFirstPlayer() {
        this.dealCards(new ItalianCardsDeckImpl());
        final ItalianCard quattroDiDenari = new ItalianCardImpl(Suit.DENARI, Value.QUATTRO);
        for (Player player : this.getPlayers()) {
            if (player.getHand().getCards().contains(quattroDiDenari)) {
                this.getTurnOrder().setNext(player);
                return player;
            }
        }
        throw new IllegalStateException();
    }

    /**
     * Utility method returning the team of a given player.
     * 
     * @param player - the player
     * @return the team of the given player
     */
    protected Team getTeamOf(final Player player) {
        for (Team team : this.getTeams()) {
            if (team.getPlayers().contains(player)) {
                return team;
            }
        }
        throw new IllegalArgumentException();
    }
}
