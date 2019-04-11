package model.entities;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import model.artificialIntelligence.BeccaccinoCardComparator;
import model.entities.ItalianCard.Suit;
import model.entities.ItalianCard.Value;

/**
 * "BunchOfCard" implementation for a game of "Beccaccino"
 */
public class BeccaccinoBunchOfCards implements BunchOfCards {

	private final List<ItalianCard> setOfCards;
	private final Map<Value, Integer> pointsMap;
	
	/**
	 * class constructor.
	 * @param setOfCards is a list of cards
	 */
	public BeccaccinoBunchOfCards(final List<ItalianCard> setOfCards) {
		this.setOfCards = setOfCards;
		this.pointsMap = new HashMap<>();
		this.createPointsMap();
	}
	
	@Override
	public List<ItalianCard> getCardsOfSuit(final Suit suit) {
		final List<ItalianCard> cardsOfSuit = new LinkedList<>();
		for(ItalianCard card: this.setOfCards) {	    
			if(card.getSuit().equals(suit)) {
				cardsOfSuit.add(card);
			}
		}
		return cardsOfSuit;
	}

	@Override
	public List<ItalianCard> getCardsOfValue(final Value value) {
		final List<ItalianCard> cardsOfValue = new LinkedList<>();
		for(ItalianCard card: this.setOfCards) {
			if(card.getValue().equals(value)) {
				cardsOfValue.add(card);
			}
		}
		return cardsOfValue;
	}

	@Override
	public int getPoints() {
		int sum = 0;
		for(ItalianCard card: this.setOfCards) {
			sum += this.pointsMap.get(card.getValue());
		}
		return sum;
	}

	@Override
	public Optional<ItalianCard> getLowestCardOfSuit(final Suit suit) {
		final BeccaccinoCardComparator comparator = new BeccaccinoCardComparator();
		final List<ItalianCard> cardsOfSuit = this.getCardsOfSuit(suit);
		cardsOfSuit.sort(comparator);
		if(!cardsOfSuit.isEmpty()) {
			return Optional.of(cardsOfSuit.get(0));
		}
		return Optional.empty();
	}

	@Override
	public Optional<ItalianCard> getHighestCardOfSuit(final Suit suit) {
		final BeccaccinoCardComparator comparator = new BeccaccinoCardComparator();
		final List<ItalianCard> cardsOfSuit = this.getCardsOfSuit(suit);
		cardsOfSuit.sort(comparator);
		if(!cardsOfSuit.isEmpty()) {
			final int indexMax = cardsOfSuit.size() - 1;
			return Optional.of(cardsOfSuit.get(indexMax));
		}
		return Optional.empty();
	}

	@Override
	public List<ItalianCard> getCardsWithMostPoints() {
		List<ItalianCard> cardsWithMorePoints = new LinkedList<>();
		int max = 0;
		for(ItalianCard card: cardsWithMorePoints) {
			int temp = this.pointsMap.get(card.getValue()); 
			if(temp > max) {
				max = temp;
				cardsWithMorePoints = new LinkedList<>();
				cardsWithMorePoints.add(card);
			}
			else if(temp == max) {
				cardsWithMorePoints.add(card);
			}
		}
		return cardsWithMorePoints;
	}

	@Override
	public List<ItalianCard> getCardsWithLeastPoints() {
		List<ItalianCard> cardsWithLeastPoint = new LinkedList<>();
		int min = 0;
		for(ItalianCard card: cardsWithLeastPoint) {
			int temp = this.pointsMap.get(card.getValue()); 
			if(temp < min) {
				min = temp;
				cardsWithLeastPoint = new LinkedList<>();
				cardsWithLeastPoint.add(card);
			}
			else if(temp == min) {
				cardsWithLeastPoint.add(card);
			}
		}
		return cardsWithLeastPoint;
	}

	@Override
	public List<ItalianCard> getHighestCards() { //e' impossibile che non torni nulla, pertanto no controlli se lista e' vuota
		final BeccaccinoCardComparator comparator = new BeccaccinoCardComparator();
		this.setOfCards.sort(comparator);
		final int indexMax = this.setOfCards.size() - 1;
		final Value maxValue = this.setOfCards.get(indexMax).getValue();
		return this.getCardsOfValue(maxValue);
	}

	@Override
	public List<ItalianCard> getLowestCards() {	//e' impossibile che non torni nulla, pertanto no controlli se lista e' vuota
		final BeccaccinoCardComparator comparator = new BeccaccinoCardComparator();
		this.setOfCards.sort(comparator);
		final Value minValue = this.setOfCards.get(0).getValue();
		return this.getCardsOfValue(minValue);
	}

	/**
	 * it is used to give each value the corresponding score
	 */
	private void createPointsMap() {
		int count = 0;
		pointsMap.put(Value.QUATTRO, count);
		pointsMap.put(Value.CINQUE, count);
		pointsMap.put(Value.SEI, count);
		pointsMap.put(Value.SETTE, count++); //incremento qua cosi a partire da "Fante" count � incrementato (count = 1)
		pointsMap.put(Value.FANTE, count);
		pointsMap.put(Value.CAVALLO, count);
		pointsMap.put(Value.RE, count);
		pointsMap.put(Value.DUE, count);
		pointsMap.put(Value.TRE, count);
		count += 2;
		pointsMap.put(Value.ASSO, count); //count = 3 --> punteggio assi
	}
}
