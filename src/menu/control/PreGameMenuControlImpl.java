package menu.control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import menu.view.MainMenuView;
import util.UtilityClass;

public class PreGameMenuControlImpl implements PreGameMenuControl {
	
	@FXML
	private ComboBox<String> boxPlayer1;
	@FXML
	private ComboBox<String> boxPlayer2;
	@FXML
	private ComboBox<String> boxPlayer3;
	@FXML
	private ComboBox<String> boxPlayer4;

	@Override
	public void backClicked(ActionEvent event) {
		
		MainMenuView.mainMenuSetup(UtilityClass.returnStageOf(event));

	}

	@Override
	public void startClicked(ActionEvent event) {
		//chiedi ad alessia per vedere cosa passare e cosa no
		UtilityClass.trialMethodForReadingFiles();
	}

}
