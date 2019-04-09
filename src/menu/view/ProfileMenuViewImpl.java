package menu.view;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import util.SceneLauncherImpl;
import util.UtilityClass;

public class ProfileMenuViewImpl implements ProfileMenuView{
	
	final static Button back = new Button("Back to Main Menu");
	
	static public void profileMenuSetup(Stage primaryStage) {
		
		Parent root;
		SceneLauncherImpl launcher = new SceneLauncherImpl("ProfileMenuScene.fxml");
		root = launcher.launchScene();
		UtilityClass.setScene(primaryStage, root);
        primaryStage.show();

	}
}
