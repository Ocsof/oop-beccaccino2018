package model.artificialIntelligence;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import model.entities.BeccaccinoBunchOfCards;
import model.entities.BunchOfCards;
import model.entities.ItalianCard;
import model.entities.ItalianCardsDeck;
import model.entities.ItalianCardsDeckImpl;
import model.entities.Play;
import model.entities.ItalianCard.Suit;
import model.logic.Round;

/**
 * It defines a basic analyzer of a game.
 */
public class GameBasicAnalyzer implements GameAnalyzer {

    protected final List<ItalianCard> handCard;
    protected final List<Partecipant> allPlayers;
    protected final List<ItalianCard> remainingCards;
    protected final List<Play> allPlays;
    protected Round currentRound;
    protected Round lastRound;
    protected final List<Round> roundPlayed;
    protected Suit briscola;

    protected final static int RIGHT = 0;
    protected final static int TEAMMATE = 1;
    protected final static int LEFT = 2;
    protected final static int ME = 3;

    protected final static int PRIMO = 0;
    protected final static int SECONDO = 1;
    protected final static int TERZO = 2;

    /**
     * Class constructor.
     * 
     * @param handCards is the AI's hand.
     */
    public GameBasicAnalyzer(final List<ItalianCard> handCards) {
        this.handCard = handCards;
        this.remainingCards = this.initializeRemainingCards(handCards);
        this.allPlayers = this.initializePlayers();
        this.allPlays = new LinkedList<>();
        this.roundPlayed = new LinkedList<>();
        this.briscola = null;
    }

    /**
     * {@inheritDoc}
     */
    public void observePlays(final Round thisRound) {
        this.currentRound = thisRound;
        this.roundPlayed.add(this.currentRound);
        // se non sono il primo del round guardo le giocate fatte
        if (!currentRound.hasJustStarted()) {
            final Counter counter = new Counter();
            List<Play> roundPlays = this.currentRound.getPlays();
            final int alreadyPlayed = roundPlays.size();
            final int first = ME - alreadyPlayed;
            for (int indexPlayer = first; indexPlayer < ME; indexPlayer++) {
                final Play playDone = roundPlays.get(counter.next());
                final Optional<String> message = playDone.getMessage();
                if (message.isPresent() && message.get().equals("BUSSO")) {
                    this.playerHasBusso(indexPlayer);
                }
                this.removeAndAdd(indexPlayer, playDone);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    public void updateLastRound() {
        if (hastheMatchStarted()) { // se non e' il primo round della partita
            final Counter counter = new Counter();
            final List<Play> roundPlays = this.lastRound.getPlays();
            final Play myLastPlay = this.allPlays.get(allPlays.size() - 1);
            // play done by RIGHT player in last round
            final int indexRightPlay = roundPlays.indexOf(myLastPlay) + 1;
            final int numPlayer = roundPlays.size();
            for (int i = indexRightPlay; i < numPlayer; i++) {
                this.removeAndAdd(counter.next(), roundPlays.get(i)); 
             // counter = 0 --> RIGHT
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    public int getWinningTeamProbability(final ItalianCard card) {
        final int probability = 100;
        if (isTeammateTempWinner()) {
            final ItalianCard cardTeammate = this.currentRound.getWinningPlay().get().getCard();
            if (willWinTheRound(cardTeammate)) {
                return probability; // 100
            }
        } else if (myRoundPositionIs(PRIMO) && hasPlayerTheBestCardOf(TEAMMATE, card.getSuit())) {
            return probability;
        } else if (myRoundPositionIs(SECONDO) && hasPlayerTheBestCardOf(TEAMMATE, this.currentRound.getSuit().get())) {
            return probability;
        } else if (isEnemyTempWinner()) { // se sta prendendo il nemico
            final BeccaccinoCardComparator comparator = new BeccaccinoCardComparator();
            final ItalianCard tempWinner = this.currentRound.getWinningPlay().get().getCard();
            if (!card.getSuit().equals(tempWinner.getSuit()) || comparator.compare(tempWinner, card) > 0) {
                return 0;
            }
        }
        // altrimenti
        return this.getWinningProbabilityOf(card);
    }

    /**
     * {@inheritDoc}
     */
    public void addMyPlay(final Play play) {
        this.lastRound = this.currentRound;
        this.allPlays.add(play);
    }

    /**
     * {@inheritDoc}
     */
    public Suit getBriscola() {
        return this.briscola;
    }

    /**
     * {@inheritDoc}
     */
    public boolean isTeammateTempWinner() {
        if (!this.currentRound.hasJustStarted()) {
            return this.allPlayers.get(TEAMMATE).getPlays().contains(this.currentRound.getWinningPlay().get());
        }
        return false;
    }

    // controllo anche se la carta che sta vincendo ha seme diverso da
    // roundSuit, se ha tagliato do per scontato che vinca
    /**
     * {@inheritDoc}
     */

    public boolean willWinTheRound(final ItalianCard tempWinnerCard) {
        if (!this.currentRound.hasJustStarted()) {
            final ItalianCard winnerCard = this.currentRound.getWinningPlay().get().getCard();
            if (tempWinnerCard.equals(winnerCard)) {
                if (isTaglio(tempWinnerCard)) {
                    return true;
                }
            }
        }
        return this.getWinningProbabilityOf(tempWinnerCard) == 100;
    }

    private boolean isTaglio(final ItalianCard card) {
        if (!this.currentRound.hasJustStarted()) {
            final Suit roundSuit = this.currentRound.getSuit().get();
            if (card.getSuit().equals(briscola) && !roundSuit.equals(card.getSuit())) {
                return true;
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    public List<ItalianCard> getRemainingCards() {
        return this.remainingCards;
    }

    /**
     * {@inheritDoc}
     */
    public Round getCurrentRound() {
        return this.currentRound;
    }

    /**
     * {@inheritDoc}
     */
    public boolean hasTempWinnerTaglio() {
        if (!currentRound.hasJustStarted()) {
            final Suit roundSuit = this.currentRound.getSuit().get();
            final Suit suitOfWinner = this.currentRound.getWinningPlay().get().getCard().getSuit();
            if (!roundSuit.equals(suitOfWinner) && suitOfWinner.equals(briscola)) {
                return true;
            }
        }
        return false;
    }

    /**
     * It set the briscola once it was chosen.
     * 
     * @param briscola is the briscola of match.
     */
    public void setBriscola(final Suit briscola) {
        this.briscola = briscola;
    }

    // *** UTILITY *****/
    /**
     * It checks if a enemy player is the temporary winner of the round.
     * 
     * @return true if he is winning, false otherwise.
     */
    protected boolean isEnemyTempWinner() {
        return !this.currentRound.hasJustStarted() && !this.isTeammateTempWinner();
    }

    /**
     * It allows to evaluate the possibility that a card could win the round,
     * without considering the cards that were played in the current round.
     * 
     * @param card is the card to evaluate.
     * @return the probability of winning.
     */
    private int getWinningProbabilityOf(final ItalianCard card) {
        final BeccaccinoCardComparator comparator = new BeccaccinoCardComparator();
        int probability = 100;
        if (!this.remainingCards.isEmpty()) {
            final BunchOfCards bunchOfCards = new BeccaccinoBunchOfCards(this.remainingCards);
            final List<ItalianCard> cardsOf = bunchOfCards.getCardsOfSuit(card.getSuit());
            cardsOf.add(card);
            cardsOf.sort(comparator);
            probability = this.calculateProbability(cardsOf, card);
        }
        return probability;
    }

    /**
     * It allows to calculate the probability that a card has to win the round.
     * 
     * @param cardsOf is a set of remaining cards with the same suit of the card
     * passed by parameter.
     * @param card is the card to calculate probability of winning round.
     * @return the probability.
     */
    protected int calculateProbability(final List<ItalianCard> cardsOf, final ItalianCard card) {
        int probability = 100;
        int maxOfSuit = cardsOf.size() - 1;
        final int indexCard = cardsOf.indexOf(card);
        if (maxOfSuit != indexCard) {
            final List<ItalianCard> cardWithMoreValue = this.remainingCardsWithMoreValue(cardsOf, indexCard);
            if (!havePlayerAllCards(TEAMMATE, cardWithMoreValue)) { // teammate
                probability = this.observeProbabilityOfEnemies(cardWithMoreValue);
            }
        }
        return probability;
    }

    /**
     * It checks if a player has all the cards.
     * 
     * @param player is the player to consider.
     * @param cardWithMoreValue are the cards to evaluate.
     * @return true if player has all the cards, false otherwise.
     */
    protected boolean havePlayerAllCards(final int player, final List<ItalianCard> cardWithMoreValue) {
        for (ItalianCard card : cardWithMoreValue) {
            if (!hasPlayerCard(player, card)) {
                return false;
            }
        }
        return true;
    }

    /**
     * It checks if a player has a card.
     * 
     * @param player is the player to consider
     * @param card is the card to consider
     * @return true if player has the card
     */
    protected boolean hasPlayerCard(final int player, final ItalianCard card) {
        return this.allPlayers.get(player).getProbabilityOf(card) == 100;
    }

    /**
     * It allows to evaluate the probabiity of winning the round consider the
     * probability of a card that is part of a set of cards better than the card
     * that is been calculated now the probability of winning.
     * 
     * @param cardsWithMoreValue is a list of card with a better value than card
     * that is been considered
     * @return the probability of winning the round
     */
    protected int observeProbabilityOfEnemies(final List<ItalianCard> cardsWithMoreValue) { 
        int winProbability = 100;
        int enemiesProbability = 0;
        for (ItalianCard card : cardsWithMoreValue) {
            if (myRoundPositionIs(PRIMO)) {
                enemiesProbability = this.allPlayers.get(RIGHT).getProbabilityOf(card)
                        + this.allPlayers.get(LEFT).getProbabilityOf(card);
            } else if (myRoundPositionIs(SECONDO) || myRoundPositionIs(TERZO)) {
                enemiesProbability = this.allPlayers.get(RIGHT).getProbabilityOf(card);
            }
            winProbability = winProbability - enemiesProbability;
        }
        if (winProbability < 0) {
            winProbability = 0;
        }
        return winProbability;
    }

    /**
     * It is useful to understand the number and which cards still have a higher
     * value than a card.
     * 
     * @param cardsOf - the list of remaining cards, ordered by value, with the
     * same suit as the evaluated card
     * @param indexCard - index of card into cardsOf
     * @return a list of cards with higher value
     */
    protected List<ItalianCard> remainingCardsWithMoreValue(final List<ItalianCard> cardsOf, final int indexCard) {
        final List<ItalianCard> remaining = new LinkedList<>();
        for (int i = indexCard + 1; i < cardsOf.size(); i++) {
            remaining.add(cardsOf.get(i));
        }
        return remaining;
    }

    /**
     * It allows to understand if the teammate "has busso" in a specific suit.
     * 
     * @param player is the player to consider
     * @param suit is the suit to consider
     * @return true if teammate "has busso" in the suit, false otherwise
     */
    protected boolean hasPlayerTheBestCardOf(final int player, final Suit suit) {
        final BunchOfCards bunchOfCards = new BeccaccinoBunchOfCards(this.remainingCards);
        if ((bunchOfCards.getHighestCardOfSuit(suit).isPresent())) {
            final ItalianCard bestCardOf = bunchOfCards.getHighestCardOfSuit(suit).get();
            if (hasPlayerCard(player, bestCardOf)) {
                return true;
            }
        }
        return false;
    }

    /**
     * It allows to update conditions of game after a "Busso".
     * 
     * @param player is the player that has "Busso".
     */
    protected void playerHasBusso(final int player) {
        if (!this.currentRound.hasJustStarted()) {
            final Suit roundSuit = this.currentRound.getSuit().get();
            final List<ItalianCard> allRemainingCard = new LinkedList<>();
            allRemainingCard.addAll(this.remainingCards);
            allRemainingCard.addAll(this.handCard);
            final BunchOfCards bunchOfCards = new BeccaccinoBunchOfCards(allRemainingCard);
            final List<ItalianCard> cardsOf = bunchOfCards.getCardsOfSuit(roundSuit);
            if (cardsOf.size() >= 2) {
                final BeccaccinoCardComparator comparator = new BeccaccinoCardComparator();
                cardsOf.sort(comparator);
                ItalianCard card = cardsOf.get(cardsOf.size() - 2);
                for (Partecipant partecipant : this.allPlayers) {
                    partecipant.setProbabilityOf(card, 0);
                }
                this.allPlayers.get(player).setProbabilityOf(card, 100);
            }
            // altrimenti non fa niente perche' il giocatore ha bussato a caso
        }
    }

    /**
     * It allows to update the condition of match after a play done.
     * 
     * @param player is the player that have done the play.
     * @param play is the play done.
     */
    protected void removeAndAdd(final int player, final Play play) {
        this.remainingCards.remove(play.getCard());
        this.allPlays.add(play); // aggiunge la giocata
        this.allPlayers.get(player).addPlay(play);
        for (Partecipant partecipant : this.allPlayers) {
            partecipant.removeCard(play.getCard());
        }
    }

    /**
     * It serves to understand the position in the round of the AI.
     * 
     * @param position is the position to check.
     * @return true if the position in the round is equal to that passed by
     * parameter.
     */
    protected boolean myRoundPositionIs(final int position) {
        return this.currentRound.getPlays().size() == position;
    }

    /**
     * It verifies that the game has started.
     * 
     * @return true if the match is started, false otherwise.
     */
    protected boolean hastheMatchStarted() {
        return !this.roundPlayed.isEmpty();
    }

    /**
     * It initializes the list of participants in the match.
     * 
     * @return the list of participants in the match.
     */
    private List<Partecipant> initializePlayers() {
        final List<Partecipant> allPlayers = new LinkedList<>();
        for (int i = RIGHT; i <= ME; i++) {
            Partecipant player = new PartecipantImpl(this.remainingCards);
            allPlayers.add(player);
        }
        return allPlayers;
    }

    /**
     * It initializes playable cards from other players.
     * 
     * @param handCards is the AI's hand
     * @return a list of playable cards from other players
     */
    private List<ItalianCard> initializeRemainingCards(final List<ItalianCard> handCards) {
        final List<ItalianCard> remainingCards = new LinkedList<>();
        final ItalianCardsDeck deck = new ItalianCardsDeckImpl();
        while (deck.remainingCards() > 0) {
            ItalianCard card = deck.drawCard();
            if (!handCards.contains(card)) {
                remainingCards.add(card);
            }
        }
        return remainingCards;
    }

}
