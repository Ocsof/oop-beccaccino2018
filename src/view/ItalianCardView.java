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
    private String name; /* the name of the image corresponding to the ItalianCard */
    private Button cardView = new Button();
    private ImageView image;
    private String sep = File.separator;

    /**
     * Class constructor.
     * 
     * @param card the ItalianCard to be represented
     */
    public ItalianCardView(final ItalianCard card) {
        this.name = card.toString() + ".jpg";
        System.out.println(card.toString());
        System.out.println(this.name);
        System.out.println(sep + "res" + sep + "cards" + sep + this.name);
        this.image = new ImageView(new Image("file:" + sep + "res" + sep + "cards" + sep + card.getValue() 
        + "di" + card.getSuit() + ".jpg"));

        image.setFitWidth(15);
        image.setFitHeight(30);
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
        back.setGraphic(new ImageView(new Image("res" + sep + "cards" + sep + "retro.jpg")));
        return back;
    }
}
