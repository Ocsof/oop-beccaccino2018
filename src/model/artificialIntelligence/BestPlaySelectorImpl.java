package model.artificialIntelligence;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import model.entities.BeccaccinoBunchOfCards;
import model.entities.BunchOfCards;
import model.entities.ItalianCard;
import model.entities.Play;
import model.entities.PlayImpl;
import model.entities.ItalianCard.Suit;
import model.entities.ItalianCard.Value;
import model.logic.Round;

/**
 * It defines an implementation of "BestPlaySelector".
 */
public class BestPlaySelectorImpl implements BestPlaySelector {

    private final GameAnalyzer game;

    /**
     * Class constructor.
     * 
     * @param game is the game analyzer of AI.
     */
    public BestPlaySelectorImpl(final GameAnalyzer game) {
        this.game = game;
    }

    // listOfCards non puo' essere vuoto
    /**
     * {@inheritDoc}
     */
    public Play doTheBestPlayFrom(final List<ItalianCard> listOfCards) {
        final ItalianCard myCard;
        Optional<String> message = Optional.empty();
        List<ItalianCard> myBestCards = new LinkedList<>();
        int max = 0;
        for (ItalianCard card : listOfCards) {
            int temp = this.game.getWinningTeamProbability(card);
            if (temp > max) {
                max = temp;
                myBestCards = new LinkedList<>();
                myBestCards.add(card);
            } else if (temp == max) {
                myBestCards.add(card);
            }
        }
        final BunchOfCards bunchOfMyBestCards = new BeccaccinoBunchOfCards(myBestCards);
        if (areWinnerCard(myBestCards)) {
            final BunchOfCards myCardsWithMostPoints = new BeccaccinoBunchOfCards(
                    bunchOfMyBestCards.getCardsWithMostPoints());
            myCard = myCardsWithMostPoints.getLowestCards().get(0);
            if (iVoloIn(myCard.getSuit())) {
                message = Optional.of("VOLO");
            }
        } else {
            // listOfCards ora non puo'essere vuoto
            return this.playLiscio(listOfCards); 
        }
        return new PlayImpl(myCard, message);
    }

    // non devo fare controlli perche' quando viene chiamato questo metodo sono
    // gia' stati fatti
    /**
     * {@inheritDoc}
     */
    public Play doTheBestTaglio() {
        final Suit briscola = this.game.getBriscola();
        final Play myPlay;
        final Round currentRound = this.game.getCurrentRound();
        final BunchOfCards bunchOfCards = new BeccaccinoBunchOfCards(currentRound.getPlayableCards());
        List<ItalianCard> myBriscole = bunchOfCards.getCardsOfSuit(briscola);
        final BunchOfCards setOfBriscole = new BeccaccinoBunchOfCards(myBriscole);
        if (game.hasTempWinnerTaglio()) {
            final ItalianCard winningCard = currentRound.getWinningPlay().get().getCard();
            myBriscole = this.compareWithMyCards(winningCard);
        }
        if (myBriscole.isEmpty()) {
            myPlay = this.playLiscio(currentRound.getPlayableCards());
        } else {
            final BunchOfCards briscoleWithMostPoints = new BeccaccinoBunchOfCards(
                    setOfBriscole.getCardsWithMostPoints());
            ItalianCard myCard = briscoleWithMostPoints.getLowestCards().get(0);
            myPlay = new PlayImpl(myCard, Optional.empty());
        }
        return myPlay;
    }

    /**
     * It allows to play the best card from the set of cards passed by
     * parameter.
     * 
     * @param listOfCards is a list of cards.
     * @return the play i have done.
     */
    private Play playLiscio(final List<ItalianCard> listOfCards) {
        final BunchOfCards bunchOfCards = new BeccaccinoBunchOfCards(listOfCards);
        final Round currentRound = this.game.getCurrentRound();
        Optional<String> message = Optional.empty();
        ItalianCard myCard = bunchOfCards.getLowestCards().get(0);
        if (currentRound.hasJustStarted()) {
            final List<ItalianCard> listOfTwo = bunchOfCards.getCardsOfValue(Value.DUE);
            if (!listOfTwo.isEmpty()) { // sarebbe utile gestire il proprio
                                        // busso cosi
                final Suit suitBusso = listOfTwo.get(0).getSuit();
                myCard = bunchOfCards.getLowestCardOfSuit(suitBusso).get();
                message = Optional.of("BUSSO");
            } else if (iVoloIn(myCard.getSuit())) {
                message = Optional.of("VOLO");
            }
        }
        return new PlayImpl(myCard, message);
    }

    /**
     * It compares the card that is winning the round with those of the AI.
     * 
     * @param winningCard is the card that is winning the round now.
     * @return a list of AI cards with higher value than the card that is
     * winning the round.
     */
    private List<ItalianCard> compareWithMyCards(final ItalianCard winningCard) {
        final Suit suit = winningCard.getSuit();
        List<ItalianCard> myBetterCardThan = new LinkedList<>();
        final Round currentRound = this.game.getCurrentRound();
        final BunchOfCards bunchOfCards = new BeccaccinoBunchOfCards(currentRound.getPlayableCards());
        final BeccaccinoCardComparator comparator = new BeccaccinoCardComparator();
        final List<ItalianCard> cardsOfRoundSuit = bunchOfCards.getCardsOfSuit(suit);
        if (!cardsOfRoundSuit.isEmpty()) {
            for (ItalianCard card : cardsOfRoundSuit) {
                if (comparator.compare(card, winningCard) > 0) {
                    myBetterCardThan.add(card);
                }
            }
        }
        return myBetterCardThan; // potrebbe essere vuoto nel caso in cui nemico
                                 // abbia tagliato e non posso tagliare
    }

    /**
     * It checks the list of cards passed by parameters will all definitely win
     * the round.
     * 
     * @param cards - list of cards to consider.
     * @return boolean if are all winner card, false otherwise.
     */
    private boolean areWinnerCard(final List<ItalianCard> cards) {
        for (ItalianCard card : cards) {
            if (!this.game.willWinTheRound(card)) {
                return false;
            }
        }
        return true;
    }

    /**
     * It verifies that the AI has only one card of a suit.
     * 
     * @param suit - the suit to check.
     * @return true if "has volo" in the suit, false otherwise.
     */
    private boolean iVoloIn(final Suit suit) {
        final Round currentRound = this.game.getCurrentRound();
        if (currentRound.hasJustStarted()) { // non deve essere iniziato il
                                              // round volare
            final BunchOfCards bunchOfCards = new BeccaccinoBunchOfCards(currentRound.getPlayableCards());
            return bunchOfCards.getCardsOfSuit(suit).size() == 1; // se e'
                                                                  // l'ultima
                                                                  // che ho in
                                                                  // mano
        }
        return false;
    }

}
