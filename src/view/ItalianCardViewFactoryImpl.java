package view;

import java.awt.Dimension;
import java.awt.Toolkit;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.entities.ItalianCard;

/**
 * Alessia Rocco 
 * ItalianCard view representation, by a rectangular Canvas and
 * the corresponding image.
 */
public class ItalianCardViewFactoryImpl implements ItalianCardViewFactory {
    private Button cardView;
    private ImageView image;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private static final int WIDTH_CARDS = 40;
    private static final int HEIGTH_CARDS = 30;
    private int width = (int) screenSize.getWidth() / ItalianCardViewFactoryImpl.WIDTH_CARDS;
    private int heigth = (int) screenSize.getWidth() / ItalianCardViewFactoryImpl.HEIGTH_CARDS;

    /**
     * Class constructor.
     */
    public ItalianCardViewFactoryImpl() {
    }

    /**
     * {@inheritDoc}
     */
    public Button getCardRepresentation(final ItalianCard card) {
        this.cardView = new Button();
        this.image = new ImageView(
                new Image(this.getClass().getResourceAsStream("/images/cards/" + card.toString() + ".jpg")));
        cardView.setGraphic(image);
        return this.cardView;
    }

    /**
     * {@inheritDoc}
     */
    public Button getBackCardRepresentation() {
        this.cardView = new Button();
        this.image = new ImageView(new Image(this.getClass().getResourceAsStream("/images/cards/retro.jpg")));
        image.setFitWidth(this.width);
        image.setFitHeight(this.heigth);
        this.cardView.setGraphic(image);
        return this.cardView;
    }
}
