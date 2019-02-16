package model;

/**
 * A classic italian playing card
 * 
 * @see https://en.wikipedia.org/wiki/Italian_playing_cards
 */

public interface ItalianCard {

	/**
	 * Italian playing cards are divided into these four suits
	 */
	public enum Suit {
		BASTONI, SPADE, DENARI, COPPE
	}

	/**
	 * Italian playing cards can assume these ten values
	 */
	public enum Value {
		ASSO, DUE, TRE, QUATTRO, CINQUE, SEI, SETTE, FANTE, CAVALLO, RE
	}

	/**
	 * @return the suit of the card
	 */
	public Suit getSuit();

	/**
	 * @return the value of the card
	 */
	public Value getValue();
}
