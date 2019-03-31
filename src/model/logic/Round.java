package model.logic;

import model.entities.ItalianCard;
import model.entities.Play;
import model.entities.Player;
import model.entities.ItalianCard.Suit;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * A round is made of a certain number of plays and by a logic that determines
 * the winning one.
 */
public interface Round {
    /**
     * Adds a play to this round, thus passing the turn to the next player (if
     * the round isn't finished yet).
     * 
     * @param play - the play to be added to this round
     */
    void addPlay(Play play); // TODO add exception

    /**
     * Returns the player on the play, only if the round isn't finished yet.
     * 
     * @return the player on the play
     */
    Player getCurrentPlayer(); // TODO add exception

    /**
     * Returns plays already made this round.
     * 
     * @return a list of this round plays, chronologically ordered
     */
    List<Play> getPlays();

    /**
     * Checks whether this round is over or not.
     * 
     * @return true if this round is over, false otherwise
     */
    boolean isOver();

    /**
     * Checks whether this round has just started or not.
     * 
     * @return true if this round has just started, false otherwise
     */
    boolean hasJustStarted();

    /**
     * Returns the winning play of this round.
     * 
     * @return an optional containing the winning play if present, an empty
     * optional otherwise
     */
    Optional<Play> getWinningPlay(); 

    /**
     * Returns cards the current player can play.
     * 
     * @return list of playable cards.
     */
    List<ItalianCard> getPlayableCards();

    /**
     * Returns messages current player can send along with chosen card.
     * 
     * @param card - chosen card
     * @return list of sendable messages.
     */
    List<Optional<String>> getSendableMessages(ItalianCard card);

    /**
     * Returns this round suit.
     * 
     * @return an optional containing this round suit if present, or an empty
     * optional otherwise.
     */
    Optional<Suit> getSuit();

    /**
     * Returns cards played this round.
     * 
     * @return a list of already played cards, chronologically ordered
     */
    default List<ItalianCard> getPlayedCards() {
        final List<ItalianCard> cards = new ArrayList<>();
        for (Play play : this.getPlays()) {
            cards.add(play.getCard());
        }
        return cards;
    }
}
