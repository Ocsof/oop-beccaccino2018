package model.entities;

import static model.entities.ItalianCard.Suit.*;
import static model.entities.ItalianCard.Value.*;
import static org.junit.Assert.*;

/**
 * JUnit test for classes implementing: ItalianCard, Hand, Player, Team
 */
public class TeamTest {
	/*
	 * Check the tests below to understand which parameters to pass to the
	 * constructors
	 */
	ItalianCard fanteDiSpade = null;
	ItalianCard cavalloDiDenari = null;
	ItalianCard assoDiBastoni = null;
	ItalianCard treDiCoppe = null;
	ItalianCard dueDiSpade = null;
	ItalianCard setteDiDenari = null;
	Player tizio = null;
	Player caio = null;
	Team lakers = null;

	@org.junit.Test
	public void testItalianCard() {
		/* Testing the method getSuit() */
		assertEquals(fanteDiSpade.getSuit(), SPADE);
		assertEquals(cavalloDiDenari.getSuit(), DENARI);
		assertEquals(assoDiBastoni.getSuit(), BASTONI);
		assertEquals(treDiCoppe.getSuit(), COPPE);
		assertEquals(dueDiSpade.getSuit(), SPADE);
		assertEquals(setteDiDenari.getSuit(), DENARI);

		/* Testing the method getValue() */
		assertEquals(fanteDiSpade.getValue(), FANTE);
		assertEquals(cavalloDiDenari.getValue(), CAVALLO);
		assertEquals(assoDiBastoni.getValue(), ASSO);
		assertEquals(treDiCoppe.getValue(), TRE);
		assertEquals(dueDiSpade.getValue(), DUE);
		assertEquals(setteDiDenari.getValue(), SETTE);
	}

	@org.junit.Test
	public void testPlayer() {
		/* Testing the method getName() */
		assertEquals(tizio.getName(), "Tizio");
		assertEquals(caio.getName(), "Caio");

		/* Testing the method isBot() */
		assertFalse(tizio.isDrivenByAI());
		assertFalse(caio.isDrivenByAI());

		/* Testing the method getHand(), and simultaneously all "Hand" methods */

		/* Testing the method isEmpty() */
		assertTrue(tizio.getHand().isEmpty());
		assertTrue(caio.getHand().isEmpty());

		/* Testing whether the other card-manipulating methods work fine */
		assertFalse(tizio.getHand().removeCard(assoDiBastoni));
		assertFalse(caio.getHand().removeCard(treDiCoppe));

		assertTrue(tizio.getHand().addCard(assoDiBastoni));
		assertTrue(tizio.getHand().addCard(fanteDiSpade));
		assertTrue(caio.getHand().addCard(cavalloDiDenari));
		assertTrue(caio.getHand().addCard(treDiCoppe));

		assertTrue(tizio.getHand().getCards().contains(assoDiBastoni));
		assertFalse(tizio.getHand().getCards().contains(treDiCoppe));

		assertTrue(caio.getHand().getCards().contains(treDiCoppe));
		assertFalse(tizio.getHand().getCards().contains(assoDiBastoni));

		assertEquals(tizio.getHand().getCards().size(), 2);
		assertEquals(caio.getHand().getCards().size(), 2);

		assertTrue(tizio.getHand().removeCard(fanteDiSpade));
		assertTrue(caio.getHand().removeCard(cavalloDiDenari));

		assertEquals(tizio.getHand().getCards().size(), 1);
		assertEquals(caio.getHand().getCards().size(), 1);
	}

	@org.junit.Test
	public void testTeam() {
		/* Testing the method getPlayers() */
		assertEquals(lakers.getPlayers().size(), 2);
		assertTrue(lakers.getPlayers().contains(tizio));
		assertTrue(lakers.getPlayers().contains(caio));

		lakers.addWonCard(fanteDiSpade);
		lakers.addWonCard(cavalloDiDenari);
		lakers.addWonCard(dueDiSpade);
		lakers.addWonCard(setteDiDenari);
		lakers.assignExtraPoints(3);

		/* Testing the method getExtraPoints() */
		assertEquals(lakers.getExtraPoints(), 3);

		/* Testing the method getWonCards() */
		assertEquals(lakers.getWonCards().size(), 4);
		assertTrue(lakers.getWonCards().contains(fanteDiSpade));
		assertTrue(lakers.getWonCards().contains(cavalloDiDenari));
		assertFalse(lakers.getWonCards().contains(treDiCoppe));
		assertFalse(lakers.getWonCards().contains(assoDiBastoni));

	}

}
