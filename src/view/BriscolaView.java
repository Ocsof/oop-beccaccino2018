package view;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javafx.scene.control.ChoiceDialog;
import javafx.stage.Stage;
import model.entities.ItalianCard;
import model.entities.ItalianCard.Suit;

/**
 * Alessia Rocco 
 * Class that implement, by an User Interface, the choice of the
 * Match's Briscola. Here implemented by a Choice Dialog.
 */
public class BriscolaView {
    private List<ItalianCard.Suit> choices;
    private ChoiceDialog<ItalianCard.Suit> dialog;
    private Optional<ItalianCard.Suit> suit;
    private Stage primaryStage;

    /**
     * Class Constructor.
     * 
     * @param primaryStage the principal Stage
     */
    public BriscolaView(final Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.choices = new LinkedList<>();
        choices.add(ItalianCard.Suit.BASTONI);
        choices.add(ItalianCard.Suit.COPPE);
        choices.add(ItalianCard.Suit.DENARI);
        choices.add(ItalianCard.Suit.SPADE);

        this.dialog = new ChoiceDialog<>(ItalianCard.Suit.BASTONI, this.choices);
        this.dialog.initOwner(this.primaryStage);
        this.dialog.setTitle("Briscola");
        this.dialog.setHeaderText("Choose your Briscola");
        this.dialog.setContentText("Briscola:");
        // rename button "annulla"
        // ((Button)
        // dialog.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("Not
        // Cancel Anymore");

        this.suit = dialog.showAndWait();
        suit.ifPresent(suit -> System.out.println("Your Briscola choice: " + suit));
        if (!this.suit.isPresent()) {
            this.suit = Optional.of(this.dialog.getDefaultChoice());
        }
    }

    /**
     * Getter of the selected Suit, chosen by the User.
     * 
     * @return an Optional.empty() if the Suit is not already set or the Suit
     * value, if Suit is present.
     */
    public final Optional<Suit> getSuit() {
        if (this.suit.isPresent()) {
            return this.suit;
        } else {
            return Optional.empty();
        }
    }

}
