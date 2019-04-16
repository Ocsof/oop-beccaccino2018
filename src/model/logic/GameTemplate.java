package model.logic;

import java.util.List;
import java.util.Optional;

import model.entities.ItalianCard.Suit;
import model.entities.ItalianCardsDeck;
import model.entities.Play;
import model.entities.Player;


/**
 * An abstract game that has a briscola concept (a dominant suit through the whole game).
 */
public abstract class GameTemplate implements Game {
    private final TurnOrder turnOrder;
    private final Player firstPlayer;
    private Optional<Suit> briscola;
    private Round currentRound;

    /**
     * 
     * @param turnOrder - //TODO
     */
    public GameTemplate(final TurnOrder turnOrder) {
        this.turnOrder = turnOrder;
        this.briscola = Optional.empty();
        this.firstPlayer = this.selectFirstPlayer();
        this.currentRound = null;
    }

    /**
     * {@inheritDoc}
     */
    public List<Player> getPlayers() {
        return this.turnOrder.getPlayers();
    }

    /**
     * {@inheritDoc}
     */
    public Round getCurrentRound() {//TODO
        if (this.currentRound == null) {
            this.currentRound = this.newRound(this.turnOrder);
        }
        return this.currentRound;
    }

    /**
     * {@inheritDoc}
     */
    public Player getCurrentPlayer() {
        if (this.currentRound == null) {
            return this.firstPlayer;
        }
        return this.currentRound.getCurrentPlayer();
    }

    /**
     * {@inheritDoc}
     */
    public void makeTurn(final Play play) {
        if (!this.briscola.isPresent()) {
            throw new IllegalStateException("Can't make a turn if a briscola suit hasn't been set yet");
        }
        if (this.currentRound == null) {
            this.currentRound = this.newRound(this.turnOrder);
            this.firstTurnRoutine(play);
        }
        this.currentRound.addPlay(play);
        this.getCurrentPlayer().getHand().removeCard(play.getCard());
        if (this.currentRound.isOver()) {
            this.roundOverRoutine();
            if (!this.isOver()) {
                this.currentRound = this.newRound(this.turnOrder);
            }
        }
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
        for (Player player : this.getPlayers()) {
            if (!player.getHand().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Deal one card to each player.
     * 
     * @param deck - needed cards are taken from here
     */
    protected void dealCards(final ItalianCardsDeck deck) {
        for (Player player : this.getPlayers()) {
            player.getHand().addCard(deck.drawCard());
        }
    }

    /**
     * @return this game turn order
     */
    protected TurnOrder getTurnOrder() {
        return this.turnOrder;
    }

    /**
     * Returns a Round. This is a factory method that allows extending this
     * class modifying the round type it will use.
     * 
     * @param turnOrder - the turn order the round should follow
     * @return a new round
     */
    protected abstract Round newRound(TurnOrder turnOrder);

    /**
     * The first round of the game can bring unique behaviors. It's up to the
     * extending class deciding which ones.
     * 
     * @param play - the play the first player is about to make.
     */
    protected abstract void firstTurnRoutine(Play play);

    /**
     * When a round is over, several things should be done. It's up to the
     * extending class.
     */
    protected abstract void roundOverRoutine();

    /**
     * Select the first player of the game. Various operations can be done in
     * order to accomplish this request.
     * 
     * @return the first player of the game
     */
    protected abstract Player selectFirstPlayer();
}
