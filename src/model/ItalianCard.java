package model;

public interface ItalianCard {
	
	enum Suit{
		BASTONI, SPADE, DENARI, COPPE
	}
	
	enum Value{
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
