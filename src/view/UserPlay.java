package view;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.entities.ItalianCard;
import model.entities.PlayImpl;
import model.logic.Game;

/**
 * Alessia Rocco Class UserPlay.
 */
public class UserPlay extends Thread {
    private Game game;
    private HBox box;
    private List<ItalianCard> tableCards;
    private Map<Node, ItalianCard> map;
    private Map<ItalianCard, Node> map2;
    private ItalianCard cardPlayed;
    private Optional<String> message;
    private PlayImpl play;
    private Stage primaryStage;
    private MessageView mess;
    private boolean waitCondition;

    /**
     * Class constructor.
     * 
     * @param game the game
     * @param boxes the boxes
     * @param tableCards the tableCards
     * @param map first map
     * @param map2 second map
     * @param primaryStage the initial stage
     * @param waitCondition the condition
     */
    public UserPlay(final Game game, final List<Node> boxes, final List<ItalianCard> tableCards,
            final Map<ItalianCard, Node> map2, final Map<Node, ItalianCard> map, final Stage primaryStage,
            final boolean waitCondition) {
        this.game = game;
        // la box contenente le carte dello user è sempre in posizione 0 della
        // lista boxes.
        this.box = (HBox) boxes.get(0);
        this.tableCards = tableCards;
        this.map = map;
        this.map2 = map2;
        this.game.getPlayers().get(0).getHand().getCards();
        this.primaryStage = primaryStage;
        this.waitCondition = waitCondition;

        this.enableCards();
    }

    // abilito le carte giocabili
    /**
     * method that, with the Hand Cards, select the only ones that are able to
     * be played.
     */
    public void enableCards() {
        for (Node card : this.box.getChildren()) {
            if (this.game.getCurrentRound().getPlayableCards().contains(this.map.get(card))) {
                card.setOnMouseClicked(s -> {
                    this.cardPlayed = this.map.get(card);
                    /*
                     * add the choice of the message associated to the card (in
                     * order to the card there are available messages.
                     */
                    mess = new MessageView(this.primaryStage, this.game, this.cardPlayed);
                    this.game.getCurrentPlayer().getHand().getCards().remove(this.cardPlayed);
                    this.tableCards.add(this.cardPlayed);
                    this.message = this.mess.getMessage();
                    // sveglio il main
                    this.waitCondition = true;
                    notify();
                });
            }
        }
    }

    /**
     * Play getter.
     * 
     * @return the play just played
     */
    public PlayImpl getPlay() {
        this.play = new PlayImpl(this.cardPlayed, this.message);
        return this.play;
    }
}
