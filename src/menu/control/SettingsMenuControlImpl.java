package menu.control;

import javafx.event.ActionEvent;
import menu.view.MainMenuView;
import util.UtilityClass;

public class SettingsMenuControlImpl implements SettingsMenuControl {

	@Override
	public void backClicked(ActionEvent event) {
		
		MainMenuView.mainMenuSetup(UtilityClass.returnStageOf(event));
		
	}
	

}
