package model.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import model.entities.BunchOfCards;
import model.entities.ItalianCard;
import model.entities.ItalianCard.Suit;
import model.entities.Play;

/**
 * This is a round of the Italian card game "Beccaccino".
 * @see <a href="Marafone Beccaccino">https://it.wikipedia.org/wiki/Marafone_Beccacino</a>
 */
public class BeccaccinoRound extends RoundTemplate {
    private static final int NUMBER_OF_TURNS = 4;
    private final Suit briscola;

    /**
     * {@inheritDoc}.
     * @param briscola - the briscola suit.
     */
    public BeccaccinoRound(final TurnOrder turnOrder, final Suit briscola) {
        super(turnOrder);
        this.briscola = briscola;
    }

    /**
     * {@inheritDoc}
     */
    public boolean isOver() {
        return this.getPlays().size() == NUMBER_OF_TURNS;
    }

    /**
     * {@inheritDoc}
     * If the round isn't over, returns the play that "is winning".
     * The hierarchy that determines which card wins is the following:
     * 1)Card with greatest value among cards of briscola suit.
     * 2)Card with greatest value among cards of this round dominant suit.
     */
    public Optional<Play> getWinningPlay() {
        final BunchOfCards playedCards = null;

        if (this.hasJustStarted()) {
            return Optional.empty();
        }
        Optional<ItalianCard> winningCard = playedCards.getHighestCardOfSuit(this.briscola);
        if (winningCard.isPresent()) {
            return this.getPlayThatContains(winningCard.get());
        }

        winningCard = playedCards.getHighestCardOfSuit(this.getSuit().get());

        return this.getPlayThatContains(winningCard.get());
    }

    /**
     * If the player is the first of the round, all cards are playable. 
     * If he isn't, he must play only cards of the dominant suit of the round. 
     * If he hasn't cards of dominant suit, all cards are playable.
     * 
     * @return list of playable cards.
     * @see {@link getSuit()}
     */
    public List<ItalianCard> getPlayableCards() {
        final List<ItalianCard> allCards = this.getCurrentPlayer().getHand().getCards();
        final List<ItalianCard> playableCards = new ArrayList<>();

        if (!this.getSuit().isPresent()) {
            return allCards;
        }
        for (ItalianCard card : allCards) {
            if (card.getSuit().equals(this.getSuit().get())) {
                playableCards.add(card);
            }
        }
        if (playableCards.size() == 0) {
            return allCards;
        }
        return playableCards;
    }

    /**
     * This is an utility method returning the suit of this round.
     * 
     * @return an optional containing the dominant suit of this round (namely
     * the suit of the first card played in this round) if present, or an empty
     * optional otherwise.
     */
    public Optional<Suit> getSuit() {
        if (this.hasJustStarted()) {
            return Optional.empty();
        }
        return Optional.ofNullable(this.getPlays().get(0).getCard().getSuit());
    }

    /**
     * This is an utility method looking for the play that contains a certain
     * card.
     * 
     * @param card - the card to be searched among the plays
     * @return an optional containing the play containing given card if present,
     * an empty optional otherwise
     */
    private Optional<Play> getPlayThatContains(final ItalianCard card) {
        if (this.hasJustStarted()) {
            return Optional.empty();
        }
        for (Play play : this.getPlays()) {
            if (play.getCard().equals(card)) {
                return Optional.ofNullable(play);
            }
        }
        return Optional.empty();
    }

    /**
     * Only the first player of the round can send messagges.
     * {@inheritDoc}
     */
    public List<Optional<String>> getSendableMessages(final ItalianCard card) {
        final List<Optional<String>> sendableMessages = new ArrayList<>();
        sendableMessages.add(Optional.empty());
        if (this.hasJustStarted()) {
            sendableMessages.add(Optional.ofNullable("BUSSO"));
            sendableMessages.add(Optional.ofNullable("STRISCIO"));
            sendableMessages.add(Optional.ofNullable("VOLO"));
        }
        return sendableMessages;
    }
}
