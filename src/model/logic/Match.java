package model.logic;


import java.util.List;
import java.util.Optional;

import model.entities.ItalianCard.Suit;
import model.entities.Play;
import model.entities.Player;
import model.entities.Team;

/**
 * A game that needs input from the outside to proceed through every turn.
 */
public interface Match {
    /**
     * @return a list of this match players
     */
    List<Player> getPlayers();

    /**
     * @return a list of the teams in this match
     */
    List<Team> getTeams();

    /**
     * @return the current round
     */
    Round getCurrentRound();

    /**
     * @return the player on the play
     */
    Player getCurrentPlayer();

    /**
     * Set this match briscola.
     * 
     * @param briscola - the suit to be set as briscola
     */
    void setBriscola(Suit briscola);

    /**
     * Get this match briscola suit, if present.
     * 
     * @return an optional containing this briscola suit, or an empty optional
     * if there isn't a briscola
     */
    Optional<Suit> getBriscola();

    /**
     * This method is the only way to proceed through the match.
     * 
     * @param play - the play that should be made
     */
    void makeTurn(Play play);

    /**
     * @return true if the match is over, false otherwise
     */
    boolean isOver();
}
