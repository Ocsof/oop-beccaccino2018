package model.artificialIntelligence;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import model.entities.ItalianCard;
import model.entities.Play;

/**
 * it is an implementation of "Partecipant" in the case of a game of Beccaccino.
 */
public class PartecipantImpl implements Partecipant {

	private static final int INITIALPROBABILITY = 33; 
	
	private final Map<ItalianCard, Integer> cardsProbability;
	private final List<Play> plays;
	
	/**
	 * class constructor.
	 * @param remainingCards are the cards still playable.
	 */
	public PartecipantImpl(final List<ItalianCard> remainingCards) {
		this.cardsProbability = this.setInitialCardProbability(remainingCards);
		this.plays = new LinkedList<>();
	}
	
	@Override
	public void addPlay(final Play play) {
	    this.plays.add(play);
	}
	
	@Override
	public List<Play> getPlays() {
		return this.plays;
	}
	
	@Override
	public void removeCard(final ItalianCard card) {
		this.cardsProbability.remove(card);
	}
	
	@Override
	public void setProbabilityOf(final ItalianCard card, final int probability) {
		this.cardsProbability.put(card, probability); 
	}

	@Override
	public int getProbabilityOf(final ItalianCard card) {
		return this.cardsProbability.get(card);
	}

	
	//*** UTILITY ****//
	
	/**
	 * it serves to set the initial probability that a card still playable is in the partecipant's hand (33 percent).
	 * @param remainingCards are the card still playable.
	 * @return a map that associates for each remaining card a probability that it is in the participant's hand (33 percent).
	 */
	private Map<ItalianCard, Integer> setInitialCardProbability(final List<ItalianCard> remainingCards) {
		final Map<ItalianCard, Integer> map = new HashMap<>();
		for(ItalianCard card : remainingCards) {
		    map.put(card, INITIALPROBABILITY);
		}
		return map;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cardsProbability == null) ? 0 : cardsProbability.hashCode());
		result = prime * result + ((plays == null) ? 0 : plays.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
	    if(this == obj) {
		return true;
	    }
	    if(obj == null) {
	        return false;
	    }
	    if(getClass() != obj.getClass()) {
		return false;
	    }
	    PartecipantImpl other = (PartecipantImpl) obj;
	    if(cardsProbability == null) {
		if(other.cardsProbability != null) {
	            return false;
		}
	    } 
	    else if(!cardsProbability.equals(other.cardsProbability)) {
		return false;
	    }
	    if(plays == null) {
		if(other.plays != null) {
		    return false;
		}
	    } 
	    else if(!plays.equals(other.plays)) {
		return false;
	    }
	    return true;
	}




	

}