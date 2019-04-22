package view;

import javafx.scene.control.Button;
import model.entities.ItalianCard;

/**
 * Alessia Rocco 
 * An interface to create the view implementation of a single
 * card.
 */
public interface ItalianCardViewFactory {
    /**
     * The method to represent a card, with its corresponding image.
     * @param card an ItalianCard
     * @return a Button for every Card, in order to view it on the table.
     */
    Button getCardRepresentation(ItalianCard card);

    /**
     * The method to represent the back of a card.
     * @return a Button for every Card, the back.
     */
    Button getBackCardRepresentation();
}
