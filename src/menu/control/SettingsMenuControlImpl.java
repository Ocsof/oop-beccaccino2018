package menu.control;

import javafx.event.ActionEvent;
import menu.view.MenuView;
import util.UtilityClass;

public class SettingsMenuControlImpl implements SettingsMenuControl {
    @Override
    public void backClicked(final ActionEvent event) {
        MenuView.menuSetup(UtilityClass.returnStageOf(event), "MainMenuScene.fxml");
    }
}
