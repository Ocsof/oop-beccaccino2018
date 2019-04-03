package view;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javafx.scene.Node;
import javafx.scene.layout.HBox;
import model.entities.ItalianCard;
import model.entities.Play;
import model.entities.PlayImpl;
import model.logic.Match;
/**
 * Alessia Rocco
 * Class UserPlay.
 */
public class UserPlay extends Thread {
    private Match match;
    private HBox box;
    private List<ItalianCard> tableCards;
    private Map<Node, ItalianCard> map;
    private ItalianCard cardPlayed;
    private Optional<String> message;
    private PlayImpl play;
    /**
     * Class constructor.
     * @param match
     * @param boxes
     * @param tableCards
     * @param map
     */
    public UserPlay(final Match match, final List<Node> boxes, final List<ItalianCard> tableCards, 
            final Map<Node, ItalianCard> map) {
        this.match = match;
        //la box contenente le carte dello user è sempre in posizione 0 della lista boxes.
        this.box = (HBox) boxes.get(0);
        this.tableCards = tableCards;
        this.map = map;
        this.match.getPlayers().get(0).getHand().getCards();
        
        this.setMessage();
        this.enableCards();
    }
    //abilito le carte giocabili
    /**
     * method that, with the Hand Cards, select the only ones that are able to be
     * played.
     */
    public void enableCards() {
        for (Node card: this.box.getChildren()) {
            if (this.match.getCurrentRound().getPlayableCards().contains(this.map.get(card))) {
                card.setOnMouseClicked(s -> {
                    this.cardPlayed = this.map.get(card);
                    this.match.getCurrentPlayer().getHand().getCards().remove(this.cardPlayed);
                    this.tableCards.add(this.cardPlayed);
                    //sveglio il main
                    notify();
                });
            }
        }
    }
    /**
     * The method in order to set the optional message.
     */
    private void setMessage() {
    }
    /**
     * Play getter.
     * @return the play just played
     */
    public PlayImpl getPlay() {
        this.play = new PlayImpl(this.cardPlayed, this.message);
        return this.play;
    }
}
