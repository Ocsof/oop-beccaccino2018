package model.logic;

import java.util.ArrayList;
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
    private List<Round> rounds;

    /**
     * 
     * @param turnOrder - the turn order this game should follow
     */
    public GameTemplate(final TurnOrder turnOrder) {
        this.turnOrder = turnOrder;
        this.briscola = Optional.empty();
        this.firstPlayer = this.selectFirstPlayer();
        this.rounds = new ArrayList<>();
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
    public Round getCurrentRound() {
        this.checkBriscolaIsPresent();
        return this.rounds.get(this.rounds.size() - 1);
    }

    /**
     * {@inheritDoc}
     */
    public Player getCurrentPlayer() {
        if (this.rounds.isEmpty()) {
            return this.firstPlayer;
        }
        return this.getCurrentRound().getCurrentPlayer();
    }

    /**
     * {@inheritDoc}
     */
    public void makeTurn(final Play play) {
        if (this.isOver()) {
            throw new IllegalStateException("Can't make a turn if the game is over");
        }
        this.checkBriscolaIsPresent();
        if (this.rounds.size() == 1) {
            this.firstTurnRoutine(play);
        }
        this.getCurrentRound().addPlay(play);
        if (this.getCurrentRound().isOver()) {
            this.roundOverRoutine();
            this.rounds.add(this.newRound(this.turnOrder));
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
     * Protection method checking the briscola has already been set.
     * If this game has no rounds yet, create the first one.
     * 
     * @throws IllegalStateException if no briscola has been set yet.
     */
    protected void checkBriscolaIsPresent() {
        if (!this.briscola.isPresent()) {
            throw new IllegalStateException("Set briscola first");
        }
        if (this.rounds.isEmpty()) {
            this.rounds.add(this.newRound(this.turnOrder));
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
