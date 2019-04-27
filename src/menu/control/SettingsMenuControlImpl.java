package menu.control;

import javafx.event.ActionEvent;
import menu.view.MenuView;
import util.UtilityClass;
/**
 * This is an implementation of the Interface SettingsMenuControl.
 */
public class SettingsMenuControlImpl implements SettingsMenuControl {
    /**
     * {@inheritDoc}
     */
    public void backClicked(final ActionEvent event) {
        MenuView.menuSetup(UtilityClass.returnStageOf(event), "MainMenuScene.fxml");
    }
}
