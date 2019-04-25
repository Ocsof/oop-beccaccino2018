package view;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Dialog;
import javafx.stage.Stage;
import model.entities.ItalianCard;
import model.logic.Game;

/**
 * Alessia Rocco 
 * Class that implement, by an User Interface, the optional
 * message the User can select. Here implemented by a Choice Dialog.
 */
public class MessageView {
    private Optional<String> message;
    private Game game;
    private ItalianCard card;
    private Stage primaryStage;
    private List<String> choice;
    private Dialog<String> dialog;
    private String defaultMessage = "NO MESSAGE";

    /**
     * Class constructor.
     * 
     * @param primaryStage the principal Stage
     * @param game the game
     * @param card the card selected
     */
    public MessageView(final Stage primaryStage, final Game game, final ItalianCard card) {
        this.primaryStage = primaryStage;
        this.game = game;
        this.card = card;
        this.choice = new LinkedList<>();

        List<Optional<String>> sm = this.game.getCurrentRound().getSendableMessages(this.card);

        for (Optional<String> s : sm) {
            if (!s.isPresent()) {
                this.choice.add(this.defaultMessage);
            } else {
                this.choice.add(s.get());
            }
        }

        this.dialog = new ChoiceDialog<>(this.choice.get(0), this.choice);
        this.dialog.initOwner(this.primaryStage);
        this.dialog.setTitle("Message");
        this.dialog.setHeaderText("Choose your Message");
        this.dialog.setContentText("Message: ");
        this.message = dialog.showAndWait();
        message.ifPresent(m -> System.out.println("Your message: " + m));
    }

    public boolean isOperationCanceled() {
        if(!this.message.isPresent()) {
            return true;
        }
        return false;
    }
    
    /**
     * Getter of the message.
     * 
     * @return the message is it's present, otherwise an empty message.
     */
    public Optional<String> getMessage() {
        if (this.message.isPresent() && !this.message.get().equals(this.defaultMessage)) {
            return this.message;
        } else {
            return Optional.empty();
        }
    }
}
