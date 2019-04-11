package model.entities;

public class ItalianCardImpl implements ItalianCard {
    
    final private Suit cardSuit;
    final private Value cardValue;
    
    public ItalianCardImpl(Suit suit, Value value) {
        
        this.cardSuit = suit;
        this.cardValue = value;
        
    }

    @Override
    public Suit getSuit() {
        
        return this.cardSuit;
        
    }

    @Override
    public Value getValue() {
        
        return this.cardValue;
        
    }

}
