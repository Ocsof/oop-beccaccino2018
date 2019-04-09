package menu.view;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import util.SceneLauncherImpl;
import util.UtilityClass;

public class SettingsMenuViewImpl implements SettingsMenuView{
	
	final static Button back = new Button("Back to Main Menu");
	
	public static void settingsMenuSetup(Stage primaryStage) {
		
		Parent root;
		SceneLauncherImpl launcher = new SceneLauncherImpl("SettingsMenuScene.fxml");
		root = launcher.launchScene();
		UtilityClass.setScene(primaryStage, root);
        primaryStage.show();
		
	}

}
