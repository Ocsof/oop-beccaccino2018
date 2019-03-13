package model.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import model.entities.ItalianCard.Suit;
import model.entities.ItalianCardsDeck;
import model.entities.Play;
import model.entities.Player;
import model.entities.Team;


/**
 * .
 *  //TODO
 */
public abstract class MatchTemplate implements Match {
    private Optional<Suit> briscola;
    private final List<Player> players;
    private final List<Team> teams;

    /**
     * 
     * @param players - //TODO
     */
    public MatchTemplate(final List<Player> players) {
        this.players = players;
        this.teams = new ArrayList<>();
        ItalianCardsDeck replace = null;
        this.dealCards(replace);
    }

    /**
     * {@inheritDoc}
     */
    public List<Player> getPlayers() {
        return this.players;
    }

    /**
     * {@inheritDoc}
     */
    public List<Team> getTeams() {
        return this.teams;
    }

    /**
     * {@inheritDoc}
     */
    public Round getCurrentRound() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public Player getCurrentPlayer() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public void makeTurn(final Play play) {
        // TODO Auto-generated method stub
    }

    /**
     * {@inheritDoc}
     */
    public void setBriscola(final Suit briscola) {
        this.briscola = Optional.ofNullable(briscola);
    }

    /**
     * {@inheritDoc}
     */
    public Optional<Suit> getBriscola() {
        return this.briscola;
    }

    /**
     * {@inheritDoc}
     */
    public boolean isOver() {
        for (Player player : players) {
            if (!player.getHand().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    /**
     * 
     * @param deck - this method takes needed cards from here
     */
    protected void dealCards(final ItalianCardsDeck deck) {
        for (Player player : this.players) {
            player.getHand().addCard(deck.drawCard());
        }
    }

}
