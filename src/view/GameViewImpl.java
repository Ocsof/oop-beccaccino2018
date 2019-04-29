package view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import controller.game.CardControllerImpl;
import controller.game.GameController;
import javafx.geometry.Insets;
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
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.entities.ItalianCard;
import model.entities.ItalianCard.Suit;
import model.entities.Player;
import model.entities.Team;
import model.logic.Game;
import model.logic.Round;

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
    private Round currentRound;
    private static final int SPACING_BETWEEN_CARDS = 20;

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
        BorderPane rootcentre = new BorderPane();

        this.boxes.add(rootbottom);
        this.boxes.add(rootright);
        this.boxes.add(roottop);
        this.boxes.add(rootleft);
        this.boxes.add(rootcentre);

        BorderPane externalPane = new BorderPane();
        Background backGround = new Background(tavolo);
        externalPane.setBackground(backGround);

        setPlayersHand(boxes);
        setPlayersName(this.game.getPlayers());

        roottop.setAlignment(Pos.TOP_CENTER);
        rootleft.setAlignment(Pos.CENTER_LEFT);
        rootright.setAlignment(Pos.CENTER_RIGHT);
        rootbottom.setAlignment(Pos.BOTTOM_CENTER);

        // add at external pane all the hands of the 4 players
        externalPane.setBottom(this.boxes.get(0));
        externalPane.setRight(this.boxes.get(1));
        externalPane.setTop(this.boxes.get(2));
        externalPane.setLeft(this.boxes.get(3));
        externalPane.setCenter(this.boxes.get(this.boxes.size() - 1));

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
        this.setBriscolaOnStage(this.game.getBriscola().get());

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
        if (this.game.getCurrentRound().getPlays().size() == 1) {
            this.currentRound = this.game.getCurrentRound();
        }

        if (this.putCardOnTheTable()) {
            AlertInformationFactory fineTurn = new AlertInformationFactory("TURN FINISHED", null,
                    "AI has just made its play", this.primaryStage);
            fineTurn.getAlert().showAndWait();
        }

        this.setBriscolaOnStage(this.game.getBriscola().get());

        if (this.game.getCurrentRound().hasJustStarted()) {
            String winnigPlay = this.currentRound.getWinningPlay().get().getCard().toString();
            AlertInformationFactory matchFinished = new AlertInformationFactory("ROUND FINISHED", null,
                    "Round win by: " + winnigPlay, this.primaryStage);
            matchFinished.getAlert().showAndWait();
            this.clearTable();
            if (this.game.isOver()) {
                final Team winningTeam;
                final Team losingTeam;
                if (this.game.getTeams().get(0).getPoints() >= this.game.getTeams().get(1).getPoints()) {
                    winningTeam = this.game.getTeams().get(0);
                    losingTeam = this.game.getTeams().get(1);
                } else {
                    winningTeam = this.game.getTeams().get(1);
                    losingTeam = this.game.getTeams().get(0);
                }
                new EndgameReport(this.primaryStage, winningTeam, losingTeam);
            }
        }
    }

    /**
     * A method in order to set cards played during a Round in the center of the
     * table.
     * 
     * @return false if the played card belonged to human player, true
     * otherwise.
     */
    private boolean putCardOnTheTable() {
        ItalianCard card = this.currentRound.getPlayedCards().get(this.currentRound.getPlayedCards().size() - 1);
        ItalianCardView c = new ItalianCardView(card);
        Optional<String> message = this.currentRound.getPlays().get(this.currentRound.getPlays().size() - 1)
                .getMessage();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = (int) screenSize.getHeight() / GameViewImpl.SPACING_BETWEEN_CARDS;
        int weight = (int) screenSize.getWidth() / GameViewImpl.SPACING_BETWEEN_CARDS;
        Text messageText;

        if (this.boxes.get(0).getChildren().contains(this.map2.get(card))) {
            HBox hbox = new HBox();
            hbox.getChildren().add(c.getCardRepresentation(card));
            if (message.isPresent()) {
                messageText = new Text("Message:" + message.get());
                hbox.getChildren().add(messageText);
            }
            hbox.setAlignment(Pos.CENTER);
            hbox.setPadding(new Insets(height));
            ((BorderPane) this.boxes.get(this.boxes.size() - 1)).setBottom(hbox);
            this.boxes.get(0).getChildren().remove(this.map2.get(card));
            return false;
        }
        if (this.boxes.get(1).getChildren().contains(this.map2.get(card))) {
            VBox vbox = new VBox();
            vbox.getChildren().add(c.getCardRepresentation(card));
            if (message.isPresent()) {
                messageText = new Text("Message:" + message.get());
                vbox.getChildren().add(messageText);
            }
            vbox.setAlignment(Pos.CENTER);
            vbox.setPadding(new Insets(weight));
            ((BorderPane) this.boxes.get(this.boxes.size() - 1)).setRight(vbox);
            this.boxes.get(1).getChildren().remove(this.map2.get(card));
            return true;
        }
        if (this.boxes.get(2).getChildren().contains(this.map2.get(card))) {
            HBox hbox = new HBox();
            hbox.getChildren().add(c.getCardRepresentation(card));
            if (message.isPresent()) {
                messageText = new Text("Message:" + message.get());
                hbox.getChildren().add(messageText);
            }
            hbox.setAlignment(Pos.BOTTOM_CENTER);
            hbox.setPadding(new Insets(height));
            ((BorderPane) this.boxes.get(this.boxes.size() - 1)).setTop(hbox);
            this.boxes.get(2).getChildren().remove(this.map2.get(card));
            return true;
        }
        if (this.boxes.get(3).getChildren().contains(this.map2.get(card))) {
            VBox vbox = new VBox();
            vbox.getChildren().add(c.getCardRepresentation(card));
            if (message.isPresent()) {
                messageText = new Text("Message:" + message.get());
                vbox.getChildren().add(messageText);
            }
            vbox.setAlignment(Pos.CENTER);
            vbox.setPadding(new Insets(weight));
            ((BorderPane) this.boxes.get(this.boxes.size() - 1)).setLeft(vbox);
            this.boxes.get(3).getChildren().remove(this.map2.get(card));
            return true;
        }
        throw new IllegalStateException("No player has the last played card");
    }

    private void clearTable() {
        this.boxes.get(4).getChildren().clear();
    }

    /**
     * A method in order to set Players' Hand in the table.
     * 
     * @param boxes: list of the five region of boxes (where the cards are
     * added)
     */
    private void setPlayersHand(final List<Pane> boxes) {
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
                Button bt = c.getBackCardRepresentation();
                this.map2.put(card, bt);
                this.map.put(bt, card);
                boxes.get(i).getChildren().add(bt);
            }
        }
    }

    private void setPlayersName(final List<Player> players) {
        int i = 0;

        for (Player player : players) {
            Text nome = new Text(player.getName());
            nome.setStyle("-fx-text-color: blue; -fx-font-size: 25px;");
            this.boxes.get(i).getChildren().add(nome);
            i++;
        }
    }

    /**
     * Put in the primaryStage the selected Briscola.
     * 
     * @param suit Suit
     */
    private void setBriscolaOnStage(final Suit suit) {
        Text briscolaArea = new Text("BRISCOLA: " + suit.toString());
        briscolaArea.setStyle("-fx-font-size: 30px;");
        ((BorderPane) this.boxes.get(this.boxes.size() - 1)).setCenter(briscolaArea);
    }
}
