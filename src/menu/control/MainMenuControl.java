package menu.control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import menu.view.MenuView;
import util.UtilityClass;
/**
 * This is an implementation of the Interface MainMenuControl.
 */
public class MainMenuControl {
    @FXML
    private BorderPane borderPane;
    /**
     * {@inheritDoc}
     */
    public void initialize() {
        UtilityClass util = new UtilityClass();
        util.setBackgroundImage(borderPane);
    }
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
