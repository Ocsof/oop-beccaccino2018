package view;

import javafx.scene.Node;
import model.entities.ItalianCard;

/**
 * Alessia Rocco
 * An interface to create the view implementation of a single card.
 */
public interface ItalianCardViewFactory {
    /**
     * 
     * @param card an ItalianCard
     * @return a Node for every Card, in order to view it on the table.
     */
    Node getCardRepresentation(ItalianCard card);
}
