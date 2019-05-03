package view;

import java.awt.Dimension;
import java.awt.Toolkit;
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
    private Button cardView;
    private ImageView image;
    private String sep = File.separator;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private static final int WIDTH_CARDS = 40;
    private static final int HEIGTH_CARDS = 30;
    private int width = (int) screenSize.getWidth() / ItalianCardView.WIDTH_CARDS;
    private int heigth = (int) screenSize.getWidth() / ItalianCardView.HEIGTH_CARDS;
    /**
     * Class constructor.
     * 
     * @param card the ItalianCard to be represented
     */
    public ItalianCardView(final ItalianCard card) {
        this.image = new ImageView(new Image("file:src/res/cards/" + card.toString() + ".jpg"));
        this.cardView = new Button();
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
        image.setFitHeight(this.heigth);
        back.setGraphic(image);
        return back;
    }
}
