package model.artificialIntelligence;

import java.util.List;

import model.entities.BeccaccinoBunchOfCards;
import model.entities.BunchOfCards;
import model.entities.ItalianCard;
import model.entities.ItalianCard.Suit;
import model.entities.ItalianCard.Value;
import model.logic.Round;

/**
 * It defines an implementation of "ConditionForTaglio".
 */
public class ConditionForTaglioImpl implements ConditionForTaglio {

    private final GameAnalyzer game;
    private final Suit briscola;

    private static final int ONECARD = 1;
    private static final int TWOPOINTS = 2;

    /**
     * Class constructor.
     * 
     * @param game is a game analyzer.
     * @param briscola is the briscola of the game.
     */
    public ConditionForTaglioImpl(final GameAnalyzer game, final Suit briscola) {
        this.game = game;
        this.briscola = briscola;
    }

    /**
     * {@inheritDoc}
     */
    public boolean areRespected() {
        final Round currentRound = this.game.getCurrentRound();
        if (!currentRound.hasJustStarted()) {
            final BunchOfCards bunchofcards = new BeccaccinoBunchOfCards(currentRound.getPlayableCards());
            if (bunchofcards.getCardsOfSuit(this.briscola).isEmpty()) {
                return false;
            }
            final Suit roundSuit = currentRound.getSuit().get();
            final ItalianCard tempWinnerCard = currentRound.getWinningPlay().get().getCard();
            if ((!roundSuit.equals(briscola)) && ((!this.game.isTeammateTempWinner())
                    || (this.game.isTeammateTempWinner() && !this.game.willWinTheRound(tempWinnerCard)))) {

                return (isAssoOfSuitStillPlayable(roundSuit) && !myLastCardOfIsTre(this.briscola))
                        || haveIAssoOf(this.briscola) || (twoPointInvolved() && !myLastCardOfIsTre(this.briscola))
                        || moreOfTwoPointInvolve();
                // anche giocando il tre di briscola
            }
        }
        return false;
    }

    /**
     * It checks if the "asso" of a suit is still playable by other players.
     * 
     * @param suit is the suit to consider.
     * @return true if the "asso" is still playable by others player, false
     * otherwise.
     */
    private boolean isAssoOfSuitStillPlayable(final Suit suit) {
        final List<ItalianCard> remainingCards = this.game.getRemainingCards();
        if (!remainingCards.isEmpty()) {
            final BunchOfCards bunchOfCards = new BeccaccinoBunchOfCards(remainingCards);
            final List<ItalianCard> cardsOf = bunchOfCards.getCardsOfSuit(suit);
            if (!cardsOf.isEmpty()) {
                final BunchOfCards bunchOfCardsOf = new BeccaccinoBunchOfCards(cardsOf);
                return !bunchOfCardsOf.getCardsOfValue(Value.ASSO).isEmpty();
            }
        }
        return false;
    }

    /**
     * It checks if "tre" is my last card of a suit.
     * 
     * @param suit is the suit to consider.
     * @return true if "tre" is my last card of the suit, false otherwise.
     */
    private boolean myLastCardOfIsTre(final Suit suit) {
        final Round currentRound = this.game.getCurrentRound();
        final BunchOfCards bunchOfCards = new BeccaccinoBunchOfCards(currentRound.getPlayableCards());
        final List<ItalianCard> cardsOf = bunchOfCards.getCardsOfSuit(suit);
        if (!cardsOf.isEmpty()) {
            final BunchOfCards bunchOfCardsOf = new BeccaccinoBunchOfCards(cardsOf);
            if (cardsOf.size() == ONECARD && !bunchOfCardsOf.getCardsOfValue(Value.TRE).isEmpty()) {
                return true;
                // se ho ancora una carta del seme e corrisponde al tre
            }
        }
        return false;
    }

    /**
     * It verifies if "asso" of a suit is in the player's hand.
     * 
     * @param suit is the to consider.
     * @return true if "asso" of the suit passed by parameters is in the
     * player's hand, false otherwise.
     */
    private boolean haveIAssoOf(final Suit suit) {
        final Round currentRound = this.game.getCurrentRound();
        final BunchOfCards bunchOfCards = new BeccaccinoBunchOfCards(currentRound.getPlayableCards());
        final BunchOfCards bunchOfCardsOf = new BeccaccinoBunchOfCards(bunchOfCards.getCardsOfSuit(suit));
        if (!bunchOfCardsOf.getCardsOfValue(Value.ASSO).isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * It verifies if the score of the cards played in the current round is
     * equals to two.
     * 
     * @return true if the score is equals to two, false otherwise.
     */
    private boolean twoPointInvolved() {
        final Round currentRound = this.game.getCurrentRound();
        final BunchOfCards bunchOfCards = new BeccaccinoBunchOfCards(currentRound.getPlayedCards());
        return bunchOfCards.getPoints() == TWOPOINTS;
    }

    /**
     * It verifies if the score of the cards played in the current round is
     * greater than two.
     * 
     * @return true if the score is greater than two, false otherwise.
     */
    private boolean moreOfTwoPointInvolve() {
        final Round currentRound = this.game.getCurrentRound();
        final BunchOfCards bunchOfCards = new BeccaccinoBunchOfCards(currentRound.getPlayedCards());
        return bunchOfCards.getPoints() > TWOPOINTS;
    }
}
