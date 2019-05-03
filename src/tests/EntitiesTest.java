package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;

import model.entities.BeccaccinoHand;
import model.entities.ItalianCard;
import model.entities.ItalianCardImpl;
import model.entities.Player;
import model.entities.PlayerImpl;
import model.entities.Team;
import model.entities.TeamImpl;
import model.entities.ItalianCard.Suit;
import model.entities.ItalianCard.Value;

/**
 * JUnit test for classes implementing: ItalianCard, Hand, Player, Team.
 */
public class EntitiesTest {
    private ItalianCard fanteDiSpade = new ItalianCardImpl(Suit.SPADE, Value.FANTE);
    private ItalianCard cavalloDiDenari = new ItalianCardImpl(Suit.DENARI, Value.CAVALLO);
    private ItalianCard assoDiBastoni = new ItalianCardImpl(Suit.BASTONI, Value.ASSO);
    private ItalianCard treDiCoppe = new ItalianCardImpl(Suit.COPPE, Value.TRE);
    private ItalianCard dueDiSpade = new ItalianCardImpl(Suit.SPADE, Value.DUE);
    private ItalianCard setteDiDenari = new ItalianCardImpl(Suit.DENARI, Value.SETTE);
    private Player tizio = new PlayerImpl("Tizio", new BeccaccinoHand());
    private Player caio = new PlayerImpl("Caio", new BeccaccinoHand());
    private List<Player> team = new LinkedList<>();
    private Team lakers = new TeamImpl(team);

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
        tizio.getHand().removeCard(assoDiBastoni);
        caio.getHand().removeCard(treDiCoppe);

        tizio.getHand().addCard(assoDiBastoni);
        tizio.getHand().addCard(fanteDiSpade);
        caio.getHand().addCard(cavalloDiDenari);
        caio.getHand().addCard(treDiCoppe);

        assertTrue(tizio.getHand().getCards().contains(assoDiBastoni));
        assertFalse(tizio.getHand().getCards().contains(treDiCoppe));

        assertTrue(caio.getHand().getCards().contains(treDiCoppe));
        assertTrue(tizio.getHand().getCards().contains(assoDiBastoni));

        assertEquals(tizio.getHand().getCards().size(), 2);
        assertEquals(caio.getHand().getCards().size(), 2);

        tizio.getHand().removeCard(fanteDiSpade);
        caio.getHand().removeCard(cavalloDiDenari);

        assertEquals(tizio.getHand().getCards().size(), 1);
        assertEquals(caio.getHand().getCards().size(), 1);
    }

    /**
     * Tests basic team functionalities.
     */
    @org.junit.Test
    public void testTeam() {
        /* Testing the method getPlayers() */
        lakers.addPlayer(tizio);
        lakers.addPlayer(caio);
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
