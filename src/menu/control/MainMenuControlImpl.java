package menu.control;

import javafx.event.ActionEvent;
import menu.view.PreGameMenuViewImpl;
import menu.view.ProfileMenuViewImpl;
import menu.view.SettingsMenuViewImpl;
import util.UtilityClass;

public class MainMenuControlImpl implements MainMenuControl{

	@Override
	public void startClicked(ActionEvent event) {
		
		PreGameMenuViewImpl.preGameMenuSetup(UtilityClass.returnStageOf(event));
		
	}

	@Override
	public void createProfileClicked(ActionEvent event) {
		
		ProfileMenuViewImpl.profileMenuSetup(UtilityClass.returnStageOf(event));
		
	}

	@Override
	public void settingsClicked(ActionEvent event) {
		
		SettingsMenuViewImpl.settingsMenuSetup(UtilityClass.returnStageOf(event));
		
	}

	@Override
	public void exitClicked() {
		
		System.exit(0);
		
	}

}
