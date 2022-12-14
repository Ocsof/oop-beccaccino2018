package model.entities;

/**
 * A classic, immutable, italian playing card.
 * 
 * @see https://en.wikipedia.org/wiki/Italian_playing_cards
 */

public interface ItalianCard {

    /**
     * Italian playing cards are divided into these four suits.
     */
    enum Suit {
        BASTONI, SPADE, DENARI, COPPE
    }

    /**
     * Italian playing cards can assume these ten values.
     */
    enum Value {
        ASSO, DUE, TRE, QUATTRO, CINQUE, SEI, SETTE, FANTE, CAVALLO, RE
    }

    /**
     * @return the suit of the card
     */
    Suit getSuit();

    /**
     * @return the value of the card
     */
    Value getValue();
}
