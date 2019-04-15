package model.artificialIntelligence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import model.entities.BeccaccinoBunchOfCards;
import model.entities.BunchOfCards;
import model.entities.ItalianCard;
import model.entities.Play;
import model.entities.ItalianCard.Suit;
import model.logic.Round;

/**
 * It defines a medium analyzer of a game. The AI remembers the suits in which
 * the other players no longer have cards.
 */
public class GameMediumAnalyzer extends GameBasicAnalyzer {

    private final Counter counter;
    private final Map<Suit, Integer> voliEachSuits;

    private static final int NUMOTHERPLAYER = 3;
    private static final int FIRSTVOLO = 1;

    /**
     * Class constructor.
     * 
     * @param myHand is the player's hand.
     */
    public GameMediumAnalyzer(final List<ItalianCard> myHand) {
        super(myHand);
        this.voliEachSuits = new HashMap<>();
        this.counter = new Counter();
    }

    /**
     * {@inheritDoc}
     */
    public void observePlays(final Round thisRound) {
        this.currentRound = thisRound;
        this.roundPlayed.add(this.currentRound);
        if (!myRoundPositionIs(PRIMO)) { // se non sono il primo del round
                                         // guardo le giocate fatte
            List<Play> roundPlays = this.currentRound.getPlays();
            final int alreadyPlayed = roundPlays.size();
            final int first = ME - alreadyPlayed;
            for (int i = first; i < ME; i++) {
                Optional<String> message = roundPlays.get(i).getMessage();
                final ItalianCard card = roundPlays.get(i).getCard();
                final Suit suit = card.getSuit();
                if (message.isPresent()) {
                    if (message.get().equals("Busso")) {
                        this.playerHasBusso(i);
                    } else if (message.get().equals("Volo")) {
                        this.finishedCardsOfSuit(i);
                    }
                } else if (this.differentFromRoundSuit(suit)) {
                    this.finishedCardsOfSuit(i);
                }
                this.removeAndAdd(i, roundPlays.get(i));
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    public void updateLastRound() {
        // se non e' il primo round della partita
        if (!this.hastheMatchStarted()) {
            final List<Play> roundPlays = this.currentRound.getPlays();
            final Play myLastPlay = this.allPlays.get(roundPlays.size() - 1);
            // indice giocatore a destra
            final int index = roundPlays.indexOf(myLastPlay) + 1;
            final int numPlayer = roundPlays.size();
            for (int i = index; i < numPlayer; i++) {
                // i messaggi non vanno gestiti perche' al massimo sono io ad
                // aver fatto
                // la prima giocata
                ItalianCard card = roundPlays.get(i).getCard();
                if (this.differentFromRoundSuit(card.getSuit())) {
                    // servirebbe un contatore, oppure: int c = RIGHT poi c++
                    this.finishedCardsOfSuit(this.counter.next());
                }
                this.removeAndAdd(i, roundPlays.get(i));
            }
        }
    }

    /**
     * It checks if a player has played a card of a suit other than the suit of
     * round.
     * 
     * @param suit is the suit of card played.
     * @return true if the two suits are equals, false otherwise.
     */
    protected boolean differentFromRoundSuit(final Suit suit) {
        if (this.currentRound.hasJustStarted()) {
            final Suit roundSuit = this.currentRound.getSuit().get();
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
        if (this.currentRound.hasJustStarted()) {
            final Suit suit = this.currentRound.getSuit().get();
            final BunchOfCards bunchOfCards = new BeccaccinoBunchOfCards(this.remainingCards);
            final List<ItalianCard> cardsOf = bunchOfCards.getCardsOfSuit(suit);
            for (ItalianCard card : cardsOf) {
                this.allPlayers.get(indexPlayer).setProbabilityOf(card, probability);
            }
            // per aumentare la prob delle carte del seme negli altri giocatori
            if (!this.voliEachSuits.containsKey(suit)) {
                this.voliEachSuits.put(suit, FIRSTVOLO);
            } else {
                final int numVoli = this.voliEachSuits.get(suit) + 1;
                this.voliEachSuits.put(suit, numVoli);
            }
            probability = 100;
            // numero giocatori che non hanno volato
            final int other = NUMOTHERPLAYER - this.voliEachSuits.get(suit);
            for (Partecipant player : this.allPlayers) {
                // non devo considerarmi
                if (this.allPlayers.indexOf(player) != ME) {
                    // se il giocatore non ha volato
                    if (!this.hasFinishedCardsOf(this.allPlayers.indexOf(player), suit)) {
                        for (ItalianCard card : cardsOf) {
                            // 100 : num giocatori che hanno volato
                            player.setProbabilityOf(card, probability / other);
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
    protected boolean hasFinishedCardsOf(final int player, final Suit suit) {
        final BunchOfCards bunchOfCards = new BeccaccinoBunchOfCards(this.remainingCards);
        final List<ItalianCard> cardsOf = bunchOfCards.getCardsOfSuit(suit);
        if (cardsOf.isEmpty()) { // se tutte le carte del seme son gia state
                                 // giocate ha volato
            return true;
        }
        for (ItalianCard card : cardsOf) {
            return this.allPlayers.get(player).getProbabilityOf(card) == 0;
        }
        return false; // non ci arrivera' mai
    }

}
