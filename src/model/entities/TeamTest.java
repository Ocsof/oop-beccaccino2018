package model.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import model.entities.ItalianCard.Suit;
import model.entities.ItalianCard.Value;

/**
 * JUnit test for classes implementing: ItalianCard, Hand, Player, Team.
 */
public class TeamTest {
    /*
     * Check the tests below to understand which parameters to pass to the
     * constructors
     */
    private ItalianCard fanteDiSpade = null;
    private ItalianCard cavalloDiDenari = null;
    private ItalianCard assoDiBastoni = null;
    private ItalianCard treDiCoppe = null;
    private ItalianCard dueDiSpade = null;
    private ItalianCard setteDiDenari = null;
    private Player tizio = null;
    private Player caio = null;
    private Team lakers = null;

    /**
     * Tests basic card functionalities.
     */
    @org.junit.Test
    public void testItalianCard() {
        /* Testing the method getSuit() */
        assertEquals(fanteDiSpade.getSuit(), Suit.SPADE);
        assertEquals(cavalloDiDenari.getSuit(), Suit.DENARI);
        assertEquals(assoDiBastoni.getSuit(), Suit.BASTONI);
        assertEquals(treDiCoppe.getSuit(), Suit.COPPE);
        assertEquals(dueDiSpade.getSuit(), Suit.SPADE);
        assertEquals(setteDiDenari.getSuit(), Suit.DENARI);

        /* Testing the method getValue() */
        assertEquals(fanteDiSpade.getValue(), Value.FANTE);
        assertEquals(cavalloDiDenari.getValue(), Value.CAVALLO);
        assertEquals(assoDiBastoni.getValue(), Value.ASSO);
        assertEquals(treDiCoppe.getValue(), Value.TRE);
        assertEquals(dueDiSpade.getValue(), Value.DUE);
        assertEquals(setteDiDenari.getValue(), Value.SETTE);
    }

    /**
     * Tests basic player functionalities.
     */
    @org.junit.Test
    public void testPlayer() {
        /* Testing the method getName() */
        assertEquals(tizio.getName(), "Tizio");
        assertEquals(caio.getName(), "Caio");

        /*
         * Testing the method getHand(), and simultaneously all "Hand" methods
         */

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

    /**
     * Tests basic team functionalities.
     */
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
        lakers.assignPoints(3);

        /* Testing the method getExtraPoints() */
        assertEquals(lakers.getPoints(), 3);

        /* Testing the method getWonCards() */
        assertEquals(lakers.getWonCards().size(), 4);
        assertTrue(lakers.getWonCards().contains(fanteDiSpade));
        assertTrue(lakers.getWonCards().contains(cavalloDiDenari));
        assertFalse(lakers.getWonCards().contains(treDiCoppe));
        assertFalse(lakers.getWonCards().contains(assoDiBastoni));

    }

}
