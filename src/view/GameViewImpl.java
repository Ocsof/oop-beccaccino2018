package view;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import controller.game.CardControllerImpl;
import controller.game.GameController;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.entities.ItalianCard;
import model.entities.ItalianCard.Suit;
import model.entities.Play;
import model.logic.Game;

/**
 * Alessia Rocco 
 * GameView Implementation.
 */
public class GameViewImpl implements GameView {
    private Game game;
    private GameController controller;
    private CardControllerImpl cardController = new CardControllerImpl();
    private Stage primaryStage;
    private final String sep = File.separator;
    private final BackgroundImage tavolo = new BackgroundImage(new Image("res" + sep + "tavolo.jpg"),
            BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
    private List<Pane> boxes = new ArrayList<>();
    private Map<Button, ItalianCard> map = new HashMap<>();
    private Map<ItalianCard, Button> map2 = new HashMap<>();

    /**
     * Class constructor.
     * 
     * @param game model.logic.Game
     * @param primaryStage the stage
     */
    public GameViewImpl(final Game game, final Stage primaryStage) {
        this.game = game;
        this.primaryStage = primaryStage;

        HBox roottop = new HBox();
        HBox rootbottom = new HBox();
        VBox rootleft = new VBox();
        VBox rootright = new VBox();
        HBox rootcentre = new HBox();
        StackPane briscolaArea = new StackPane();
        this.boxes.add(rootbottom);
        this.boxes.add(rootright);
        this.boxes.add(roottop);
        this.boxes.add(rootleft);
        this.boxes.add(rootcentre);
        this.boxes.add(briscolaArea);

        BorderPane externalPane = new BorderPane();
        Background backGround = new Background(tavolo);
        externalPane.setBackground(backGround);

        setPlayersHand(boxes);

        roottop.setAlignment(Pos.TOP_CENTER);
        rootleft.setAlignment(Pos.CENTER_LEFT);
        rootright.setAlignment(Pos.CENTER_RIGHT);
        rootbottom.setAlignment(Pos.BOTTOM_CENTER);
        rootcentre.setAlignment(Pos.CENTER);

        // add at external pane all the hands of the 4 players
        externalPane.setBottom(this.boxes.get(0));
        externalPane.setRight(this.boxes.get(1));
        externalPane.setTop(this.boxes.get(2));
        externalPane.setLeft(this.boxes.get(3));
        externalPane.setCenter(this.boxes.get(4));

        Scene scene = new Scene(externalPane);
        this.primaryStage.setScene(scene);
        this.primaryStage.setTitle("BECCACCINO");
        this.primaryStage.setFullScreen(true);
        this.primaryStage.show();
    }

    /**
     * {@inheritDoc}
     */
    public void setController(final GameController controller) {
        this.controller = controller;
    }

    /**
     * {@inheritDoc}
     */
    public void allowUserPlay() {
        for (Node card : ((Pane) this.boxes.get(0)).getChildren()) {
            if (this.game.getCurrentRound().getPlayableCards().contains(this.map.get(card))) {
                card.setDisable(false);
                card.setOnMouseClicked(s -> {
                    Button b = (Button) s.getSource();
                    MessageView m = new MessageView(primaryStage, game, this.map.get(b));
                    if (!m.isOperationCanceled()) {
                        this.cardController.action(this.map.get(b), m.getMessage());
                        this.disableButtons();
                        this.controller.notifyUserHasPlayed(cardController.getPlay());
                    }
                });
            } else {
                card.setDisable(true);
            }

            card.setOnMouseEntered(en -> {
                Button bt = (Button) en.getSource();
                bt.setMinHeight(bt.getHeight() * 2);
                bt.setMinWidth(bt.getWidth() * 2);
            });

            card.setOnMouseExited(ex -> {
                Button bt = (Button) ex.getSource();
                bt.setMinHeight(bt.getHeight() / 2);
                bt.setMinWidth(bt.getWidth() / 2);
            });
        }
    }

    private void disableButtons() {
        for (Node card : ((Pane) this.boxes.get(0)).getChildren()) {
            card.setDisable(true);
        }
    }

    /**
     * {@inheritDoc}
     */
    public Suit getSelectedBriscola() {
        BriscolaView briscola = new BriscolaView(this.primaryStage);
        this.setBriscolaOnStage(briscola.getSuit().get());
        return briscola.getSuit().get();
    }

    /**
     * {@inheritDoc}
     */
    public void update() {
        this.setBriscolaOnStage(this.game.getBriscola().get());
        if (!this.game.getCurrentRound().getPlays().isEmpty()) {
            Play lastPlay = this.game.getCurrentRound().getPlays()
                    .get(this.game.getCurrentRound().getPlays().size() - 1);
            Button lastCard = this.map2.get(lastPlay.getCard());

            if (this.boxes.get(0).getChildren().contains(lastCard)) {
                this.boxes.get(0).getChildren().remove(lastCard);
            }

            this.setTableCards(this.boxes);
        }
    }

    /**
     * A method in order to set cards played during a Round in the center of the
     * table.
     * 
     * @param boxes list of the five region of boxes (where the cards are added)
     */
    private void setTableCards(final List<Pane> boxes) {
        this.boxes.get(4).getChildren().clear();
//        try {
//            Thread.sleep(10);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        for (ItalianCard card : this.game.getCurrentRound().getPlayedCards()) {
            ItalianCardViewFactory c = new ItalianCardView(card);
            ((Pane) this.boxes.get(4)).getChildren().add(c.getCardRepresentation(card));
        }
    }

    /**
     * A method in order to set Players' Hand in the table.
     * 
     * @param boxes: list of the five region of boxes (where the cards are
     * added)
     */
    private void setPlayersHand(final List<Pane> boxes) {
        for (Pane b : this.boxes) {
            b.getChildren().clear();
        }

        for (ItalianCard card : game.getPlayers().get(0).getHand().getCards()) {
            ItalianCardViewFactory c = new ItalianCardView(card);
            Button bt = c.getCardRepresentation(card);
            this.map2.put(card, bt);
            this.map.put(bt, card);
            boxes.get(0).getChildren().add(bt);
        }

        for (int i = 1; i < 4; i++) {
            for (ItalianCard card : game.getPlayers().get(i).getHand().getCards()) {
                ItalianCardViewFactory c = new ItalianCardView(card);
                this.map2.put(card, c.getBackCardRepresentation());
                this.map.put(c.getBackCardRepresentation(), card);
                Button bt = c.getBackCardRepresentation();
                boxes.get(i).getChildren().add(bt);
            }
        }
    }

    /**
     * Put in the primaryStage the selected Briscola.
     * 
     * @param suit Suit
     */
    private void setBriscolaOnStage(final Suit suit) {

    }
}
