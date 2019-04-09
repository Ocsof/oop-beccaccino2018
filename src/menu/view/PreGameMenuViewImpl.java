package menu.view;

import javafx.scene.Parent;
import javafx.stage.Stage;
import util.SceneLauncherImpl;
import util.UtilityClass;

public class PreGameMenuViewImpl {

	public static void preGameMenuSetup(Stage primaryStage) {
		
		Parent root;
		SceneLauncherImpl launcher = new SceneLauncherImpl("PreGameScene.fxml");
		root = launcher.launchScene();
		UtilityClass.setScene(primaryStage, root);
		primaryStage.show();
		
	}

}
