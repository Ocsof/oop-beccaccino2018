package model.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import model.entities.ItalianCard;
import model.entities.ItalianCard.Suit;
import model.entities.Play;

public class BeccaccinoRound extends RoundTemplate {
    private static final int NUMBER_OF_TURNS = 4;
    private final Suit briscola;

    public BeccaccinoRound(final TurnOrder turnOrder, final Suit briscola) {
        super(turnOrder);
        this.briscola = briscola;
    }

    @Override
    public boolean isOver() {
        return this.getPlays().size() == NUMBER_OF_TURNS;
    }

    @Override
    public Play getWinningPlay() {
        this.checkIfOver(true);
        /*
         * final Suit roundSuit = this.getPlays().get(0).getCard().getSuit();
         * final List<ItalianCard> cards = new ArrayList<>(); for (Play play :
         * this.getPlays()) { cards.add(play.getCard()); }
         */
        return null;
    }

    @Override
    public List<ItalianCard> getPlayableCards(List<ItalianCard> allCards) {
        if (!this.getSuit().isPresent()) {
            return allCards;
        }
        final List<ItalianCard> playableCards = new ArrayList<>();
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

    private Optional<Suit> getSuit() {
        if (this.getPlays().size() == 0) {
            return Optional.empty();
        }
        return Optional.ofNullable(this.getPlays().get(0).getCard().getSuit());
    }

}
