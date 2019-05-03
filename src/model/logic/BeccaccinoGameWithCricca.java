package model.logic;

import java.util.ArrayList;
import java.util.List;

import model.entities.ItalianCard;
import model.entities.ItalianCardImpl;
import model.entities.Play;
import model.entities.Team;
import model.entities.ItalianCard.Value;

/**
 * A beccaccino game that rewards the first player with extra points if he has cricca.
 */
public class BeccaccinoGameWithCricca extends BeccaccinoGame {
    private static final int CRICCA_POINTS = 9;

    /**
     *@param turnOrder - the turn order this game should follow
     * @param team1 - first team
     * @param team2 - second team
     */
    public BeccaccinoGameWithCricca(final TurnOrder turnOrder, final Team team1, final Team team2) {
        super(turnOrder, team1, team2);
    }

    /**
     * If the first player has ASSO, DUE, TRE of the briscola suit and he plays
     * first the ASSO, his team gains immediately extra points.
     * 
     * @param play - this turn play
     */
    protected void firstTurnRoutine(final Play play) {
        final ItalianCard assoDiBriscola = new ItalianCardImpl(this.getBriscola().get(), Value.ASSO);
        final ItalianCard dueDiBriscola = new ItalianCardImpl(this.getBriscola().get(), Value.DUE);
        final ItalianCard treDiBriscola = new ItalianCardImpl(this.getBriscola().get(), Value.TRE);
        final List<ItalianCard> cricca = new ArrayList<>();
        cricca.add(assoDiBriscola);
        cricca.add(dueDiBriscola);
        cricca.add(treDiBriscola);

        if (play.getCard().equals(assoDiBriscola)) {
            if (this.getCurrentPlayer().getHand().getCards().containsAll(cricca)) {
                this.getTeamOf(this.getCurrentPlayer()).assignPoints(CRICCA_POINTS);
            }
        }
    }

}
