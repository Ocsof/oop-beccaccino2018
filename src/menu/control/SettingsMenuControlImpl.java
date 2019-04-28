package menu.control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import menu.view.MenuView;
import util.UtilityClass;
/**
 * This is an implementation of the Interface SettingsMenuControl.
 */
public class SettingsMenuControlImpl implements SettingsMenuControl {
    @FXML
    private CheckBox musicBox;
    @FXML
    private CheckBox voiceBox;
    @FXML
    private CheckBox soundFXBox;
    @FXML
    private CheckBox criccaBox;
    /**
     * {@inheritDoc}
     */
    public void backClicked(final ActionEvent event) {
        MenuView.menuSetup(UtilityClass.returnStageOf(event), "MainMenuScene.fxml");
    }
    /**
     * {@inheritDoc}
     */
    public void initialize() {
        
    }
}
