package menu.control;

import javafx.event.ActionEvent;
import menu.view.MainMenuView;
import util.UtilityClass;

public class ProfileMenuControlImpl implements ProfileMenuControl{

	@Override
	public void confirmationClicked() {
		
	}

	@Override
	public void backClicked(ActionEvent event) {
		
		MainMenuView.mainMenuSetup(UtilityClass.returnStageOf(event));
		
	}

	@Override
	public void createClicked() {
		
	}
	
	

}
