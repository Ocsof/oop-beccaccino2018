package model.artificialIntelligence;

import java.util.List;

import model.entities.ItalianCard;
import model.entities.Play;
import model.entities.ItalianCard.Suit;
import model.logic.Round;

/**
 * Database useful for AI to memorize important information in order to play the
 * best card in a round.
 */

public interface GameAnalyzer {

    /**
     * It allows the AI to observe the plays already played in the current round
     * before playing his card.
     * 
     * @param thisRound is the current round.
     */
    void observePlays(Round thisRound);

    /**
     * it allows the AI to memorize the plays already played in the last round
     * starting from his play.
     * 
     */
    void updateLastRound();

    /**
     * It calculates the probability that the team could win the round by
     * playing a card.
     * 
     * @param card is the card to consider.
     * @return the probability of winning against the opponent's cards.
     */
    int getWinningTeamProbability(ItalianCard card);

    /**
     * It serves the AI to remember the play made in this round.
     * 
     * @param play made by the AI in the current round.
     */
    void addMyPlay(Play play);

    /**
     * It returns the Briscola of match.
     * 
     * @return briscola of match.
     */
    Suit getBriscola();

    /**
     * It checks if the teammate of AI is the temporary winner of the round.
     * 
     * @return true if he is the temporary winner, false otherwise.
     */
    boolean isTeammateTempWinner();

    /**
     * It checks the card passed as a parameter will definitely win the round.
     * 
     * @param card is the to consider.
     * @return true if the card will win the round, false otherwise.
     */
    boolean willWinTheRound(ItalianCard card);

    /**
     * It returns the cards still playable by other player.
     * 
     * @return a list of remaining cards.
     */
    List<ItalianCard> getRemainingCards();

    /**
     * It returns the current round.
     * 
     * @return the current round.
     */
    Round getCurrentRound();

    /**
     * It verifies if the temporary winner of round has "tagliato".
     * 
     * @return true if the temporary winner "has tagliato", false otherwise.
     */
    boolean hasTempWinnerTaglio();

}
