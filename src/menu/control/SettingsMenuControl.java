package menu.control;

import javafx.event.ActionEvent;
/**
 * This Interface contains all controller methods regarding the Settings Menu Scene.
 */
public interface SettingsMenuControl {
    /**
     * Controller method for the pressing of the Back button on the Settings Menu.
     * @param event - the event triggered by the pressing of the Back Button.
     */
    void backClicked(ActionEvent event);
    /**
     * This is the method called by the FXML file after the injection of all FXML items.
     */
    void initialize();
}
