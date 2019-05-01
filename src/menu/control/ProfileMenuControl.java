package menu.control;

import javafx.event.ActionEvent;
/**
 * This Interface contains all controller methods regarding the Profile Menu Scene.
 */
public interface ProfileMenuControl {
    /**
     * This is the method called by the FXML file after the injection of all FXML items.
     */
    void initialize();
    /**
     * Controller method for the pressing of the Back button on the Profile Menu.
     * @param event - the event triggered by the pressing of the Back Button.
     */
    void backClicked(ActionEvent event);
    /**
     * Controller method for the pressing of the Create Profile button on the Profile Menu.
     * @param event - the event triggered by the pressing of the Create Profile Button.
     */
    void createClicked(ActionEvent event);
}
