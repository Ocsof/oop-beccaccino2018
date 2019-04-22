package view;

import java.awt.Toolkit;
import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import controller.game.CardController;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.entities.ItalianCard;
import model.entities.ItalianCard.Suit;
import model.entities.ItalianCard.Value;
import model.entities.ItalianCardImpl;
import model.entities.Play;
import model.entities.PlayImpl;
import model.logic.Game;

/**
 * Alessia Rocco 
 * GameView Implementation.
 */
public class GameViewImpl implements GameView {
    private Game game;
    private final int size = 80;
    private final String sep = File.separator;
    private final BackgroundImage tavolo = new BackgroundImage(new Image("res" + sep + "tavolo.jpg"),
            BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
    private List<Pane> boxes = new LinkedList<>();
    private List<ItalianCard> tableCards = new LinkedList<>();
    private Stage primaryStage;
    private Map<Button, ItalianCard> map = new HashMap<>();
    private Map<ItalianCard, Button> map2 = new HashMap<>();

    /**
     * Class constructor.
     * 
     * @param game model.logic.Game
     */
    public GameViewImpl(final Game game) {
        this.game = game;
    }

    /**
     * The method to set up the Stage of the initial game.
     * 
     * @param primaryStage the Stage.
     */
    public void initialGameSetUp(final Stage primaryStage) {
        this.primaryStage = primaryStage;
        HBox roottop = new HBox();
        HBox rootbottom = new HBox();
        VBox rootleft = new VBox();
        VBox rootright = new VBox();
        HBox rootcentre = new HBox();
        this.boxes.add(rootbottom);
        this.boxes.add(rootright);
        this.boxes.add(roottop);
        this.boxes.add(rootleft);
        this.boxes.add(rootcentre);
        BorderPane externalPane = new BorderPane();
        Background backGround = new Background(tavolo);

        setPlayersHand(boxes);

        externalPane.setBackground(backGround);

        /*
         * qui da risistemare i numeri secchi con delle proporzioni e togliere
         * magic number sostituendoli con una variabile.
         */
        roottop.setPadding(new Insets(10, 10, 10, this.size * 5));
        rootleft.setPadding(new Insets(this.size, 0, 0, 0));
        rootright.setPadding(new Insets(this.size, 0, 0, 0));
        rootbottom.setPadding(new Insets(10, 10, 10, this.size * 5));
        // add at extern pane all the hands of the 4 players
        externalPane.setBottom(this.boxes.get(0));
        externalPane.setRight(this.boxes.get(1));
        externalPane.setTop(this.boxes.get(2));
        externalPane.setLeft(this.boxes.get(3));
        externalPane.setCenter(this.boxes.get(this.boxes.size() - 1));

        Scene scene = new Scene(externalPane);
        this.primaryStage.setScene(scene);
        this.primaryStage.setTitle("BECCACCINO");
        this.primaryStage.setHeight(Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 0.8);
        this.primaryStage.setWidth(Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.8);
        this.primaryStage.show();
    }

    /**
     * A method in order to set Players' Hand in the table.
     * 
     * @param boxes: list of the five region of boxes (where the cards are
     * added)
     */
    private void setPlayersHand(final List<Pane> boxes) {
        /*
         * setting the 4 ItalianCardsDeck: clockwise, starting from the bottom.
         */
        for (ItalianCard card : game.getPlayers().get(0).getHand().getCards()) {
            ItalianCardViewFactory c = new ItalianCardView(card);
            this.map2.put(card, c.getCardRepresentation(card));
            this.map.put(c.getCardRepresentation(card), card);
            boxes.get(0).getChildren().add(c.getBackCardRepresentation());
        }

        for (int i = 1; i < 4; i++) {
            for (ItalianCard card : game.getPlayers().get(i).getHand().getCards()) {
                ItalianCardViewFactory c = new ItalianCardView(card);
                boxes.get(i).getChildren().add(c.getBackCardRepresentation());
            }
        }
    }

    /**
     * A method in order to set cards played during a Round in the center of the
     * table.
     * 
     * @param boxes list of the five region of boxes (where the cards are added)
     */
    private void setTableCards(final List<Pane> boxes) {
        for (ItalianCard card : this.tableCards) {
            ItalianCardViewFactory c = new ItalianCardView(card);
            ((Pane) boxes.get(boxes.size() - 1)).getChildren().add(c.getCardRepresentation(card));
        }
    }

    /**
     * {@inheritDoc}
     */
    public synchronized Play getUserPlay() {
        /*
         * aggancio action listener: attivo le carte giocabili (vedi metodo in
         * GameViewObserver) condwait su Playscelta quando verremo "svegliati"
         * abbiamo la play, controllo che la currentPlay sia piena e la ritorno.
         */
        for (Node card : ((Pane) this.boxes.get(1)).getChildren()) {
            if (this.game.getCurrentRound().getPlayableCards().contains(this.map.get(card))) {
                card.setOnMouseClicked(s -> {
                    /*
                     * add the choice of the message associated to the card (in
                     * order to the card there are available messages.
                     */
                    CardController cardController = new CardController();
                    cardController.action();
                });
            }
        }
//
//        try {
//            wait();
//        } catch (InterruptedException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public Suit getSelectedBriscola() {
        BriscolaView briscola = new BriscolaView(this.primaryStage);
        return briscola.getSuit().get();
    }

    /**
     * {@inheritDoc}
     */
    public void renderPlay() {
        /*
         * visualizza la carta giocata, la ruota e la toglie dalla mano del
         * giocatore poi se è l'ultima giocata toglie le carte e le assegna a
         * chi ha vinto il turno. altrimenti si passa al prossimo.
         */
        List<Play> p = this.game.getCurrentRound().getPlays();
        Play lastPlay = p.get(p.size() - 1);
        this.tableCards.add(lastPlay.getCard());
        Node lastPlayNode = this.map2.get(lastPlay.getCard());
        this.map2.remove(lastPlay.getCard());
        this.map.remove(lastPlayNode);

        if (this.boxes.get(0).getChildren().contains(lastPlayNode)) {
            this.boxes.get(0).getChildren().remove(lastPlayNode);
        } else if (this.boxes.get(1).getChildren().contains(lastPlayNode)) {
            this.boxes.get(1).getChildren().remove(lastPlayNode);
        } else if (this.boxes.get(2).getChildren().contains(lastPlayNode)) {
            this.boxes.get(2).getChildren().remove(lastPlayNode);
        } else if (this.boxes.get(3).getChildren().contains(lastPlayNode)) {
            this.boxes.get(3).getChildren().remove(lastPlayNode);
        }

//        this.boxes.get(4).getChildren().add(lastPlayNode);
        this.setPlayersHand(this.boxes);
        this.setTableCards(this.boxes);
    }
}
