package view;

import java.io.File;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.entities.ItalianCard;

/**
 * Alessia Rocco 
 * ItalianCard view representation, by a rectangular Canvas and
 * the corresponding image.
 */
public class ItalianCardView implements ItalianCardViewFactory {
    private String name; // the name of the image corresponding to
                         // thebItalianCard
    private Button cardView;
    private ImageView image;
    private String sep = File.separator;
    private int width = 20;
    private int height = 40;

    /**
     * Class constructor.
     * 
     * @param card the ItalianCard to be represented
     */
    public ItalianCardView(final ItalianCard card) {
        this.name = card.getValue() + " di" + "\n" + card.getSuit();
        this.image = new ImageView(new Image("file:" + sep + "res" + sep + "cards" + sep + card.toString() + ".jpg"));
        this.cardView = new Button(this.name);
        cardView.setGraphic(image);
    }

    /**
     * {@inheritDoc}
     */
    public Button getCardRepresentation(final ItalianCard card) {
        return this.cardView;
    }

    /**
     * {@inheritDoc}
     */
    public Button getBackCardRepresentation() {
        Button back = new Button();
        this.image = new ImageView(new Image("res" + sep + "cards" + sep + "retro.jpg"));
        image.setFitWidth(this.width);
        image.setFitHeight(this.height);
        back.setGraphic(image);
        return back;
    }
}
