package model.artificialIntelligence;

import model.entities.Play;
import model.entities.Player;
import model.entities.ItalianCard.Suit;
import model.logic.Round;

/**
 * It defines an implementation of the AI.
 */
public class AIImpl implements AI {
    private final Player me;
    private BriscolaSelector selector;
    private final GameAnalyzer game;
    private final BestPlaySelector chooser;
    private ConditionForTaglio conditionForTaglio;

    /**
     * Class constructor.
     * 
     * @param player is the virtual player associated with the AI.
     * @param game is a game analyzer useful to act in the best way in a game.
     */
    public AIImpl(final Player player, final GameAnalyzer game) {
        this.me = player;
        this.selector = new BriscolaSelectorImpl(this.me.getHand().getCards());
        this.game = game;
        this.chooser = new BestPlaySelectorImpl(this.game);
    }

    /**
     * {@inheritDoc}
     */
    public Play makePlay(final Round currentRound) {
        this.game.updateLastRound();
        this.game.observePlays(currentRound);
        final Play myPlay;
        if (!currentRound.hasJustStarted()) {
            if (this.conditionForTaglio.areRespected()) { // if he can taglio
                myPlay = this.chooser.doTheBestTaglio();
            } else {
                myPlay = this.chooser.doTheBestPlayFrom(currentRound.getPlayableCards());
            }
        } else { //is the first to play in the round
            myPlay = this.chooser.doTheBestPlayFrom(currentRound.getPlayableCards());
        }
        this.game.addMyPlay(myPlay);
        return myPlay;
    }

    /**
     * {@inheritDoc}
     */
    public Suit selectBriscola() {
        final Suit briscola = this.selector.getPreferredSuit();
        this.conditionForTaglio = new ConditionForTaglioImpl(this.game, briscola);
        return briscola;
    }

    /**
     * {@inheritDoc}
     */
    public void setBriscola(final Suit briscola) {
        this.game.setBriscola(briscola);
        this.conditionForTaglio = new ConditionForTaglioImpl(this.game, briscola);
    }

    /**
     * {@inheritDoc}
     */
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((me == null) ? 0 : me.hashCode());
        return result;
    }

    /**
     * {@inheritDoc}
     */
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        AIImpl other = (AIImpl) obj;
        if (me == null) {
            if (other.me != null) {
                return false;
            }
        } else if (!me.equals(other.me)) {
            return false;
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    public String toString() {
        return this.me.toString();
    }

}
