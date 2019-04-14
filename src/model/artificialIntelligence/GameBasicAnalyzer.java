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

    protected final List<Partecipant> allPlayers;
    protected final List<ItalianCard> remainingCards;
    protected final List<Play> allPlays;
    protected Round currentRound;
    protected Round lastRound;
    protected final List<Round> roundPlayed;
    protected final Suit briscola;

    protected final static int RIGHT = 0;
    protected final static int TEAMMATE = 1;
    protected final static int LEFT = 2;
    protected final static int ME = 3;

    protected final static int PRIMO = 0;
    protected final static int SECONDO = 0;

    /**
     * Class constructor.
     * 
     * @param myHand is the player's hand.
     * @param briscola of the game.
     */
    public GameBasicAnalyzer(final List<ItalianCard> myHand, final Suit briscola) {
        this.allPlayers = this.initializePlayers();
        this.remainingCards = this.initializeRemainingCards(myHand);
        this.allPlays = new LinkedList<>();
        this.roundPlayed = new LinkedList<>();
        this.briscola = briscola;
    }

    /**
     * {@inheritDoc}
     */
    public void observePlays(final Round thisRound) {
        this.currentRound = thisRound;
        this.roundPlayed.add(this.currentRound);
        if (currentRound.hasJustStarted()) { // se non sono il primo del round
                                             // guardo le giocate fatte
            List<Play> roundPlays = this.currentRound.getPlays();
            final int alreadyPlayed = roundPlays.size();
            final int first = ME - alreadyPlayed;
            for (int i = first; i < ME; i++) {
                Optional<String> message = roundPlays.get(i).getMessage();
                if (message.isPresent() && message.get().equals("Busso")) {
                    this.playerHasBusso(i);
                }
                this.removeAndAdd(i, roundPlays.get(i));
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    public void updateLastRound() {
        if (hastheMatchStarted()) { // se non e' il primo round della partita
            final List<Play> roundPlays = this.lastRound.getPlays();
            final Play myLastPlay = this.allPlays.get(roundPlays.size() - 1);
            final int rightPlayerPosition = roundPlays.indexOf(myLastPlay) + 1;
            final int numPlayer = roundPlays.size();
            for (int i = rightPlayerPosition; i < numPlayer; i++) {
                this.removeAndAdd(i, roundPlays.get(i));
            }
        }
    }

    /*
     * nuova versione in cui ritorna: - -1 se sta prendendo il nemico e la carta
     * parametro � piu piccola; - -1 se il nemico ha tagliato e la carta non �
     * di briscola - la getWinningProbabilityOf pu� restituire come minimo 0
     */
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
        } else if (myRoundPositionIs(PRIMO) && hasPlayerBussoIn(TEAMMATE, card.getSuit())) {
            return probability;
        } else if (myRoundPositionIs(SECONDO) && hasPlayerBussoIn(TEAMMATE, this.currentRound.getSuit().get())) {
            return probability;
        } else if (isEnemyTempWinner()) { // se sta prendendo il nemico
            final BeccaccinoCardComparator comparator = new BeccaccinoCardComparator();
            final ItalianCard tempWinner = this.currentRound.getWinningPlay().get().getCard();
            if (!card.getSuit().equals(tempWinner.getSuit()) || comparator.compare(tempWinner, card) > 0) {
                return -1;
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
        if (this.currentRound.hasJustStarted()) {
            return this.allPlayers.get(TEAMMATE).getPlays().contains(this.currentRound.getWinningPlay().get());
        }
        return false;
    }

    // lo si chiama solo sulla carta che sta vincendo il round
    // controllo anche se la carta che sta vincendo ha seme diverso da
    // roundSuit, se ha tagliato do per scontato che vinca
    /**
     * {@inheritDoc}
     */
    public boolean willWinTheRound(final ItalianCard card) {
        if (this.currentRound.hasJustStarted()) {
            final Suit roundSuit = this.currentRound.getSuit().get();
            final ItalianCard tempWinnerCard = this.currentRound.getWinningPlay().get().getCard();
            return card.equals(tempWinnerCard)
                    && (!roundSuit.equals(card.getSuit()) || this.getWinningProbabilityOf(card) == 100);
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
        if (currentRound.hasJustStarted()) {
            final Suit roundSuit = this.currentRound.getSuit().get();
            final Suit suitOfWinner = this.currentRound.getWinningPlay().get().getCard().getSuit();
            if (!roundSuit.equals(suitOfWinner) && suitOfWinner.equals(briscola)) {
                return true;
            }
        }
        return false;
    }

    // *** UTILITY *****/
    /**
     * It checks if a enemy player is the temporary winner of the round.
     * 
     * @return true if he is winning, false otherwise.
     */
    protected boolean isEnemyTempWinner() {
        return this.currentRound.hasJustStarted() && !this.isTeammateTempWinner();
    }

    // nuova versione in cui se la probabilita di prendere � meno di zero viene
    // messa a zero
    // non viene piu chiamato se c'e' una tempWinnerCard che ha seme diverso da
    // card, viene intercettato prima
    // solo su carte con value piu alto e dello stesso seme di quella che sta
    // eventualmente vincendo il round
    /**
     * It allows to evaluate the possibility that a card could win the round,
     * without considering the cards that were played in the current round.
     * 
     * @param card to evaluate.
     * @return the probability of winning.
     */
    protected int getWinningProbabilityOf(final ItalianCard card) {
        final BunchOfCards bunchOfCards = new BeccaccinoBunchOfCards(this.remainingCards);
        final BeccaccinoCardComparator comparator = new BeccaccinoCardComparator();
        int probability = 100;
        final List<ItalianCard> cardsOf = bunchOfCards.getCardsOfSuit(card.getSuit());
        cardsOf.sort(comparator);
        if (this.remainingCards.contains(card)) {
            probability = this.calculateProbability(cardsOf, card);
        } else { // e' una carta che e' nella mia mano
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
     * @param card to calculate probability of winning round.
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
     * @param player to consider.
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
     * @param player to consider
     * @param card to consider
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
        int enemiesProbability;
        for (ItalianCard card : cardsWithMoreValue) {
            if (myRoundPositionIs(PRIMO)) {
                enemiesProbability = this.allPlayers.get(RIGHT).getProbabilityOf(card)
                        + this.allPlayers.get(LEFT).getProbabilityOf(card);
            } else {
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
        final int indexMaxValue = cardsOf.size() - 1;
        for (int i = indexCard + 1; i <= indexMaxValue; i++) {
            remaining.add(cardsOf.get(i));
        }
        return remaining;
    }

    // considero la bussata del teammate se ha la carta piu' alta del seme, se
    // non ce l'ha non lo considero il busso
    // ad esempio se nel turno precedente quando aveva bussato non e' stato
    // giocato il tre allora non ha senso considerare
    // il busso
    /**
     * It allows to understand if the teammate "has busso" in a specific suit.
     * 
     * @param player to consider
     * @param suit to consider
     * @return true if teammate "has busso" in the suit, false otherwise
     */
    protected boolean hasPlayerBussoIn(final int player, final Suit suit) {
        final BunchOfCards bunchOfCards = new BeccaccinoBunchOfCards(this.remainingCards);
        if ((bunchOfCards.getHighestCardOfSuit(suit).isPresent())) {
            if (hasPlayerCard(player, bunchOfCards.getHighestCardOfSuit(suit).get())) {
                return true;
            }
        }
        return false;
    }

    // lascio aperta la possibilita che possa bussare se ha la seconda carta piu
    // alta di un seme tra le rimanenti
    /**
     * It allows to update conditions of game after a "Busso".
     * 
     * @param player that has "Busso".
     */
    protected void playerHasBusso(final int player) {
        if (this.currentRound.hasJustStarted()) {
            final Suit roundSuit = this.currentRound.getSuit().get();
            final BunchOfCards bunchOfCards = new BeccaccinoBunchOfCards(this.remainingCards);
            final List<ItalianCard> cardsOf = bunchOfCards.getCardsOfSuit(roundSuit);
            if (cardsOf.size() >= 2) {
                cardsOf.remove(bunchOfCards.getHighestCardOfSuit(roundSuit).get());
                ItalianCard card = cardsOf.get(cardsOf.size() - 1);
                final int probability = 100;
                this.allPlayers.get(player).setProbabilityOf(card, probability);
            }
            // altrimenti non fa niente perche' il giocatore ha bussato a caso
        }
    }

    /**
     * It allows to update the condition of match after a play done.
     * 
     * @param player that have done the play.
     * @param play done.
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
     * @param position to check.
     * @return true if the position in the round corresponds to that passed by
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
     * @param myCards
     * @return a list of playable cards from other players
     */
    private List<ItalianCard> initializeRemainingCards(final List<ItalianCard> myCards) {
        final List<ItalianCard> remainingCards = new LinkedList<>();
        final ItalianCardsDeck deck = new ItalianCardsDeckImpl();
        while (deck.remainingCards() > 0) {
            ItalianCard card = deck.drawCard();
            if (!myCards.contains(card)) {
                remainingCards.add(card);
            }
        }
        return remainingCards;
    }

}
