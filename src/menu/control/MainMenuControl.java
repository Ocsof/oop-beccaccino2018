package menu.control;

import javafx.event.ActionEvent;
/**
 * This Interface contains all controller methods regarding the Main Menu Scene.
 */
public interface MainMenuControl {
    /**
     * This is the method called by the FXML file after the injection of all FXML items.
     */
    void initialize();
    /**
     * Controller method for the pressing of the Start button on the Main Menu.
     * @param event - the event triggered by the pressing of the Start Button.
     */
    void startClicked(ActionEvent event);
    /**
     * Controller method for the pressing of the Profiles button on the Main Menu.
     * @param event - the event triggered by the pressing of the Profiles Button.
     */
    void createProfileClicked(ActionEvent event);
    /**
     * Controller method for the pressing of the Settings button on the Main Menu.
     * @param event - the event triggered by the pressing of the Settings Button.
     */
    void settingsClicked(ActionEvent event);
    /**
     * Controller method for the pressing of the Exit button on the Main Menu.
     * Calls a System.exit.
     */
    void exitClicked();
}
