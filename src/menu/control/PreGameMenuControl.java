package menu.control;

import javafx.event.ActionEvent;
/**
 * This is the Controller Interface for the Pre Game Menu.
 */
public interface PreGameMenuControl {
    /**
     * This is the method called by the FXML file after the injection of all FXML items.
     */
    void initialize();
    /**
     * Controller method for the pressing of the Start Button button on the Pre-Game Menu.
     * @param event - the event triggered by the pressing of the Start Button.
     */
    void startClicked(ActionEvent event);
    /**
     * Controller method for the pressing of the Back Button button on the Pre-Game Menu.
     * @param event - the event triggered by the pressing of the Back Button.
     */
    void backClicked(ActionEvent event);

}
