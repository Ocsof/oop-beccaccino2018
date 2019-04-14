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
    private Suit briscola;
    private BriscolaSelector selector;
    private final GameAnalyzer game;
    private final BestPlaySelector chooser;
    private ConditionForTaglio conditionForTaglio;

    // nel ruleset sarà inizializzato il tipo di game --> e' lui che definisce l'AI
    /**
     * Class constructor.
     * 
     * @param player is the virtual player associated with the AI
     * @param game is a game analyzer useful to act in the best way in a game.
     */
    public AIImpl(final Player player, final GameBasicAnalyzer game) {
        this.me = player;
        this.selector = new BriscolaSelectorImpl(this.me.getHand().getCards());
        this.game = game;
        this.chooser = new BestPlaySelectorImpl(this.game);
    }

    /*
     * in questa nuova versione i controlli: - se sta prendendo il nemico --->
     * gameAnalyzer metodo combinato con la getTeamProbability - se sta
     * prendendo il nemico tagliando ---> gameAnalyzer metodo combinato con la
     * getTeamProbability - se posso tagliare ---> conditionTaglio - carta
     * migliore da giocare che sia del seme del round oppure un liscio di un
     * seme se non ce l'ho --> BestPlaySelector
     */
    /**
     * {@inheritDoc}
     */
    public Play makePlay(final Round currentRound) {
        this.game.updateLastRound();
        this.game.observePlays(currentRound);
        final Play myPlay;
        if (currentRound.hasJustStarted()) {
            if (this.conditionForTaglio.areRespected()) { // posso tagliare
                myPlay = this.chooser.doTheBestTaglio();
            } else {
                myPlay = this.chooser.doTheBestPlayFrom(currentRound.getPlayableCards());
            }
        } else { // sono il primo a giocare nel round
            myPlay = this.chooser.doTheBestPlayFrom(currentRound.getPlayableCards());
        }
        this.game.addMyPlay(myPlay);
        return myPlay;
    }

    /**
     * {@inheritDoc}
     */
    public Suit selectBriscola() {
        this.briscola = this.selector.getPreferredSuit();
        this.conditionForTaglio = new ConditionForTaglioImpl(this.game, this.briscola);
        return this.briscola;
    }

    /**
     * {@inheritDoc}
     */
    public void setBriscola(final Suit suit) {
        this.briscola = suit;
    }

}