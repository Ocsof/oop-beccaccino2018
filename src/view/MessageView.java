package view;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Dialog;
import javafx.stage.Stage;
import model.entities.ItalianCard;
/**
 * Alessia Rocco
 * Class that implement, by an User Interface, the optional message the User can select.
 * Here implemented by a Choice Dialog.
 */
public class MessageView {
    /**
     * Messages can be send during the play.
     */
    public enum Messages{
        BUSSO, STRISCIO, VOLO;
    }
    private Optional<String> message;
    private Stage primaryStage;
    private List<Messages> choice;
    private Dialog dialog;
    /**
     * Class constructor.
     * @param primaryStage
     */
    public MessageView(final Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.choice = new LinkedList<>();
        choice.add(Messages.BUSSO);
        choice.add(Messages.STRISCIO);
        choice.add(Messages.VOLO);

        this.dialog = new ChoiceDialog<>(ItalianCard.Suit.BASTONI, this.choice);
        this.dialog.initOwner(this.primaryStage);
        this.dialog.setTitle("Briscola");
        this.dialog.setContentText("Choose your Briscola:");

        this.message = dialog.showAndWait();
        message.ifPresent(suit -> System.out.println("Your choice: " + suit));
    }
    /**
     * Getter of the message.
     * @return the message is it's present, otherwise an empty message.
     */
    public Optional<String> getMessage(){
        if (this.message.isPresent()) {
            return this.message;
        }
        return Optional.empty();
    }
}
