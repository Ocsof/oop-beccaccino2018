package view;

import java.awt.Toolkit;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.entities.ItalianCard;
import model.entities.ItalianCard.Suit;
import model.entities.Play;
import model.entities.PlayImpl;
import model.logic.Match;
/**
 * Alessia Rocco
 * GameView Implementation.
 */
public class GameViewImpl implements GameView {
    private Match match;
    private final int size = 80;
    private final BackgroundImage tavolo = new BackgroundImage(new Image("res/tavolo.jpg"),
            BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
    private List<Node> boxes = new LinkedList<>();
    private List<ItalianCard> tableCards = new LinkedList<>();
    private Stage primaryStage;
    private Map<Node, ItalianCard> map = new HashMap<>();
    /**
     * Class constructor.
     * @param match model.Match
     */
    public GameViewImpl(final Match match) {
        this.match = match;
    }
    /**
     * The method to set up the Stage of the initial game.
     * @param primaryStage the Stage.
     */
    public void initialGameSetUp(final Stage primaryStage) {
        this.primaryStage = primaryStage;
        HBox roottop = new HBox();
        HBox rootbottom = new HBox();
        VBox rootleft = new VBox();
        VBox rootright = new VBox();
        this.boxes.add(rootbottom);
        this.boxes.add(rootright);
        this.boxes.add(roottop);
        this.boxes.add(rootleft);
        Pane table = new BorderPane();
        BorderPane externalPane = new BorderPane();
        Background backGround = new Background(tavolo);

        setPlayersHand(boxes);

        externalPane.setBackground(backGround);

        /*qui da risistemare i numeri secchi con delle proporzioni e togliere magic number
         * sostituendoli con una variabile.
         */
        roottop.setPadding(new Insets(10, 10, 10, this.size * 5));
        rootleft.setPadding(new Insets(this.size, 0, 0, 0));
        rootright.setPadding(new Insets(this.size, 0, 0, 0));
        rootbottom.setPadding(new Insets(10, 10, 10, this.size * 5));
        // aggiunta al pane esterno tutte le mani delle carte
        externalPane.setBottom(this.boxes.get(0));
        externalPane.setRight(this.boxes.get(1));
        externalPane.setTop(this.boxes.get(2));
        externalPane.setLeft(this.boxes.get(3));
        externalPane.setCenter(table);

        Scene scene = new Scene(externalPane);
        this.primaryStage.setScene(scene);
        this.primaryStage.setTitle("BECCACCINO");
        this.primaryStage.setHeight(Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 0.8);
        this.primaryStage.setWidth(Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.8);
        this.primaryStage.show();
    }
    /**
     * A method in order to set Players' Hand in the table.
     * @param boxes: list of the four region of boxes (where the cards are added)
     */
    private void setPlayersHand(final List<Node> boxes) {
     /*
      *setting the 4 ItalianCardsDeck: clockwise, starting from the bottom.
      */
     for (int i = 0; i < boxes.size(); i++) {
         for (ItalianCard card: match.getPlayers().get(i).getHand().getCards()) {
             ItalianCardViewFactory c = null;
             this.map.put(c.getCardRepresentation(card), card);
             ((Pane) boxes.get(i)).getChildren().add(c.getCardRepresentation(card));
         }
     }
    }

    @Override
    public Play getUserPlay() {
        /*aggancio action listener: attivo le carte giocabili (vedi metodo in GameViewObserver)
        *condwait su Playscelta
        *quando verremo "svegliati" abbiamo la play, controllo che la currentPlay 
        *sia piena e la ritorno.
        */
        //creo il nuovo thread che si occupa della giocata
        UserPlay userPlay = new UserPlay(this.match, this.boxes, this.tableCards, this.map);
        userPlay.start();
        //WAIT DEL MAIN THREAD
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //qui in teoria lascio la parola alla classe UserPlay che esegue tutto
        //SVEGLIO IL MAINTHREAD
        PlayImpl play = userPlay.getPlay();
        this.tableCards.add(play.getCard());
        this.map.remove(play.getCard());
        return play;
    }

    @Override
    public Suit getSelectedBriscola() {
        /*fare in modo di selezionare una briscola in modo view, 
         * settare un campo briscola e poi ritornarlo.
         * come getUserPlay, una nuova finestra con 4 bottoni da selezionare, 
         * non si deve oscurare la roba sotto (una DIALOG).
         */
        BriscolaView briscola = new BriscolaView(this.primaryStage);
        return briscola.getSuit().get();
    }

    @Override
    public void renderPlay() {
        /*visualizza la carta giocata, la ruota e la toglie dalla mano del giocatore
         * poi se è l'ultima giocata toglie le carte e le assegna a chi ha vinto il turno.
         * altrimenti si passa al prossimo.
         */
        List<Play> p = this.match.getCurrentRound().getPlays();
        Play lastPlay = p.get(p.size() - 1);
        this.tableCards.add(lastPlay.getCard());
        this.map.remove(lastPlay.getCard());
    }
}
