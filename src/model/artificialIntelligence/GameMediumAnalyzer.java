package model.artificialIntelligence;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import model.entities.BeccaccinoBunchOfCards;
import model.entities.BunchOfCards;
import model.entities.ItalianCard;
import model.entities.Play;
import model.entities.ItalianCard.Suit;

/**
 * It defines a medium analyzer of a game. The AI remembers the suits in which
 * the other players no longer have cards.
 */
public class GameMediumAnalyzer extends GameBasicAnalyzer {

    private final Map<Integer, List<Suit>> voliEachPlayer;

    private static final int NUMOTHERPLAYER = 3;

    /**
     * Class constructor.
     * 
     * @param myHand is the player's hand.
     */
    public GameMediumAnalyzer(final List<ItalianCard> myHand) {
        super(myHand);
        this.voliEachPlayer = new HashMap<>();
        this.voliEachPlayer.put(ME, new LinkedList<>());
        this.voliEachPlayer.put(RIGHT, new LinkedList<>());
        this.voliEachPlayer.put(TEAMMATE, new LinkedList<>());
        this.voliEachPlayer.put(LEFT, new LinkedList<>());
    }

    /**
     * {@inheritDoc}
     */
    public int getWinningTeamProbability(final ItalianCard card) {
        final int probability = 100;
        if (isTeammateTempWinner()) {
            final ItalianCard cardTeammate = this.getCurrentRound().getWinningPlay().get().getCard();
            if (willWinTheRound(cardTeammate)) {
                return probability; // 100
            }
        } else if (myRoundPositionIs(PRIMO) && hasPlayerTheBestCardOf(TEAMMATE, card.getSuit())
                && !couldEnemiesTaglio(card.getSuit())) {
            return probability;
        } else if (myRoundPositionIs(SECOND)) {
            final Suit roundSuit = this.getCurrentRound().getSuit().get();
            if (hasPlayerTheBestCardOf(TEAMMATE, roundSuit) || couldTeammateTaglio(roundSuit)) {
                if (!couldEnemiesTaglio(roundSuit)) {
                    return probability;
                }
            }
        } else if (isEnemyTempWinner()) { // se sta prendendo il nemico
            final BeccaccinoCardComparator comparator = new BeccaccinoCardComparator();
            final ItalianCard tempWinner = this.getCurrentRound().getWinningPlay().get().getCard();
            if (!card.getSuit().equals(tempWinner.getSuit()) || comparator.compare(tempWinner, card) > 0) {
                return 0;
            }
        }
        return this.getWinningProbabilityOf(card);
    }

    /**
     * {@inheritDoc}
     */
    public boolean willWinTheRound(final ItalianCard tempWinnerCard) {
        if (!getCurrentRound().hasJustStarted()) {
            final Suit roundSuit = this.getCurrentRound().getSuit().get();
            final ItalianCard winnerCard = this.getCurrentRound().getWinningPlay().get().getCard();
            if (tempWinnerCard.equals(winnerCard)) {
                if (isTaglio(tempWinnerCard) && !couldEnemiesTaglio(roundSuit)) {
                    return true;
                }
            }
        }
        return this.getWinningProbabilityOf(tempWinnerCard) == 100;
    }

    // ********UTILITY**************

    /**
     * {@inheritDoc}
     */
    protected void observePlaysCurrentRound(final int firstPlay, final int lastPlay) {
        final Counter counter = new Counter();
        final List<Play> roundPlays = this.getCurrentRound().getPlays();
        for (int indexPlay = firstPlay; indexPlay <= lastPlay; indexPlay++) {
            final Play playDone = roundPlays.get(counter.next());
            final Optional<String> message = playDone.getMessage();
            final Suit suit = playDone.getCard().getSuit();
            if (message.isPresent()) {
                if (message.get().equals("BUSSO")) {
                    this.playerHasBusso(indexPlay);
                } else if (message.get().equals("VOLO")) {
                    this.finishedCardsOfSuit(indexPlay);
                }
            } else if (this.differentFromRoundSuit(suit)) {
                this.finishedCardsOfSuit(indexPlay);
            }
            this.removeAndAdd(indexPlay, playDone);
        }
    }

    /**
     * {@inheritDoc}
     */
    protected void observePlaysLastRound(final int rightPlay, final int lastPlay) {
        // counter = 0 --> RIGHT
        final Counter counter = new Counter();
        final List<Play> roundPlays = this.getLastRound().getPlays();
        for (int i = rightPlay; i < lastPlay; i++) {
            final ItalianCard card = roundPlays.get(i).getCard();
            int cont = counter.next();
            if (this.differentFromRoundSuit(card.getSuit())) {
                this.finishedCardsOfSuit(cont);
            }
            this.removeAndAdd(cont, roundPlays.get(i));
        }
    }

    /**
     * {@inheritDoc} It consider as better cards also those that have the
     * briscola suit that could be played by enemies.
     */
    protected List<ItalianCard> remainingBetterCard(final ItalianCard card, final List<ItalianCard> cardsOf) {
        if (!couldEnemiesTaglio(card.getSuit())) {
            return super.remainingBetterCard(card, cardsOf);
        }
        final List<ItalianCard> remainingBetterCard = super.remainingBetterCard(card, cardsOf);
        final BunchOfCards bunchOfCards = new BeccaccinoBunchOfCards(this.getRemainingCards());
        for (ItalianCard cardOfBriscola : bunchOfCards.getCardsOfSuit(getBriscola())) {
            remainingBetterCard.add(cardOfBriscola);
        }
        return remainingBetterCard;
    }

    /**
     * It checks if a player has played a card of a suit other than the suit of
     * round.
     * 
     * @param suit is the suit of card played.
     * @return true if the two suits are equals, false otherwise.
     */
    protected boolean differentFromRoundSuit(final Suit suit) {
        if (!this.getCurrentRound().hasJustStarted()) {
            final Suit roundSuit = this.getCurrentRound().getSuit().get();
            return !roundSuit.equals(suit);
        }
        return false;
    }

    /**
     * It is used to update the AI after a player no longer has the cards in a
     * suit.
     * 
     * @param indexPlayer is the player who has finished the cards of the round
     * suit.
     */
    protected void finishedCardsOfSuit(final int indexPlayer) {
        int probability = 0;
        if (!this.getCurrentRound().hasJustStarted()) {
            final Suit roundSuit = this.getCurrentRound().getSuit().get();
            // solo se non aveva volato nel seme in precedenza rifattorizzo le
            // probabilità delle carte
            if (!hadAlreadyFinishedCardsOf(indexPlayer, roundSuit)) {
                final List<Suit> voliPlayer = this.voliEachPlayer.get(indexPlayer);
                voliPlayer.add(roundSuit);
                final BunchOfCards bunchOfCards = new BeccaccinoBunchOfCards(this.getRemainingCards());
                final List<ItalianCard> cardsOf = bunchOfCards.getCardsOfSuit(roundSuit);
                for (ItalianCard card : cardsOf) {
                    this.getAllPlayer().get(indexPlayer).setProbabilityOf(card, probability);
                }
                int numVoliRoundSuit = 0;
                for (int player : this.voliEachPlayer.keySet()) {
                    if (this.voliEachPlayer.get(player).contains(roundSuit)) {
                        numVoliRoundSuit++;
                    }
                }
                probability = 100;
                // numero giocatori che non hanno volato
                final int other = NUMOTHERPLAYER - numVoliRoundSuit;
                for (Partecipant player : this.getAllPlayer()) {
                    // non devo considerarmi
                    if (this.getAllPlayer().indexOf(player) != ME) {
                        // se il giocatore non ha volato
                        if (!this.hadAlreadyFinishedCardsOf(this.getAllPlayer().indexOf(player), roundSuit)) {
                            if (other != 0) {
                                for (ItalianCard card : cardsOf) {
                                    // 100 : num giocatori che non hanno volato
                                    player.setProbabilityOf(card, probability / other);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * It checks if a player has finished cards in a suit.
     * 
     * @param player is the player to check.
     * @param suit is the suit to check.
     * @return true if the player has finished cards in the suit, false
     * otherwise.
     */
    protected boolean hadAlreadyFinishedCardsOf(final int player, final Suit suit) {
        return this.voliEachPlayer.get(player).contains(suit);
    }

    /**
     * It checks whether the enemies could "taglio" a suit.
     * 
     * @param suit is the suit to evaluate.
     * @return true if enemies could "taglio" the suit evaluated.
     */
    protected boolean couldEnemiesTaglio(final Suit suit) {
        if (myRoundPositionIs(PRIMO)) {
            return couldPlayerTaglio(LEFT, suit) || couldPlayerTaglio(RIGHT, suit);
        } else if (myRoundPositionIs(SECOND) || myRoundPositionIs(THIRD)) {
            return couldPlayerTaglio(RIGHT, suit);
        }
        return false;
    }

    /**
     * It checks whether the teammate could "taglio" a suit.
     * 
     * @param suit is the suit to evaluate.
     * @return true if teammate could "taglio" the suit evaluated.
     */
    protected boolean couldTeammateTaglio(final Suit suit) {
        if (myRoundPositionIs(PRIMO) || myRoundPositionIs(SECOND)) {
            return couldPlayerTaglio(TEAMMATE, suit);
        }
        return false;
    }

    /**
     * It checks whether a player could "taglio" a suit.
     * 
     * @param indexPlayer is the player to evaluate.
     * @param suit is the suit to evaluate.
     * @return true if the player could "taglio" the suit evaluated.
     */
    protected boolean couldPlayerTaglio(final int indexPlayer, final Suit suit) {
        if (!suit.equals(this.getBriscola())) {
            if (!this.getRemainingCards().isEmpty()) {
                final BunchOfCards bunchOfRemaininCards = new BeccaccinoBunchOfCards(this.getRemainingCards());
                final List<ItalianCard> cardsOf = bunchOfRemaininCards.getCardsOfSuit(suit);
                if (!cardsOf.isEmpty()) {
                    final List<ItalianCard> cardsOfBriscola = bunchOfRemaininCards.getCardsOfSuit(this.getBriscola());
                    if (!cardsOfBriscola.isEmpty()) {
                        return (!this.voliEachPlayer.get(indexPlayer).contains(suit))
                                && (this.voliEachPlayer.get(indexPlayer).contains(this.getBriscola()));
                    }
                }
            }
        }
        return false;
    }
}
