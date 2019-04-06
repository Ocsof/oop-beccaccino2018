package view;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Dialog;
import javafx.stage.Stage;
import model.entities.ItalianCard;
import model.logic.Match;
/**
 * Alessia Rocco
 * Class that implement, by an User Interface, the optional message the User can select.
 * Here implemented by a Choice Dialog.
 */
public class MessageView {
    private Optional<String> message;
    private Match match;
    private ItalianCard card;
    private Stage primaryStage;
    private List<Optional<String>> choice;
    private Dialog dialog;
    /**
     * Class constructor.
     * @param primaryStage
     * @param match
     * @param card
     */
    public MessageView(final Stage primaryStage, final Match match, final ItalianCard card) {
        this.primaryStage = primaryStage;
        this.match = match;
        this.card = card;
        this.choice = new LinkedList<>();
        choice.addAll(this.match.getCurrentRound().getSendableMessages(this.card));

        this.dialog = new ChoiceDialog<>(this.choice.get(0), this.choice);
        this.dialog.initOwner(this.primaryStage);
        this.dialog.setTitle("Briscola");
        this.dialog.setContentText("Choose your Briscola:");

        this.message = dialog.showAndWait();
        message.ifPresent(m -> System.out.println("Your message: " + m));
    }
    /**
     * Getter of the message.
     * @return the message is it's present, otherwise an empty message.
     */
    public Optional<String> getMessage() {
        if (this.message.isPresent()) {
            return this.message;
        }
        return Optional.empty();
    }
}
