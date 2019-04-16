package view;

import java.io.File;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.entities.ItalianCard;

/**
 * Alessia Rocco
 * ItalianCard view representation, by a rectangular Canvas and
 * the corresponding image.
 */
public class ItalianCardView implements ItalianCardViewFactory {
    private ItalianCard.Suit suit;
    private ItalianCard.Value value;
    private String name; // the name of the image corresponding to the
                         // ItalianCard
    private ImageView cardView;
    private Image image;

    /**
     * Class constructor.
     * 
     * @param card the ItalianCard to be represented
     */
    public ItalianCardView(final ItalianCard card) {
        this.suit = card.getSuit();
        this.value = card.getValue();
        this.name = this.value + "di" + this.suit + ".jpg";
        this.image = new Image("file:res" + File.separator + this.name);
        this.cardView = new ImageView();
        this.cardView.setImage(image);
    }

    /**
     * {@inheritDoc}
     */
    public Node getCardRepresentation(final ItalianCard card) {
        return this.cardView;
    }

}
