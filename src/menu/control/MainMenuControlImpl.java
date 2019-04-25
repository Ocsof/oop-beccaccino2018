package menu.control;

import javafx.event.ActionEvent;
import menu.view.MenuView;
import util.UtilityClass;
/**
 * This is an implementation of the Interface MainMenuControl.
 */
public class MainMenuControlImpl implements MainMenuControl {
    /**
     * {@inheritDoc}
     */
    public void startClicked(final ActionEvent event) {
        MenuView.menuSetup(UtilityClass.returnStageOf(event), "PreGameScene.fxml");
    }
    /**
     * {@inheritDoc}
     */
    public void createProfileClicked(final ActionEvent event) {
        MenuView.menuSetup(UtilityClass.returnStageOf(event), "ProfileMenuScene.fxml");
    }
    /**
     * {@inheritDoc}
     */
    public void settingsClicked(final ActionEvent event) {
        MenuView.menuSetup(UtilityClass.returnStageOf(event), "SettingsMenuScene.fxml");
    }
    /**
     * {@inheritDoc}
     */
    public void exitClicked() {
        System.exit(0);
    }

}
