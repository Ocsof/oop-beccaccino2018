package menu.view;

import javafx.animation.FadeTransition;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.util.Duration;
import util.SceneLauncherImpl;
import util.UtilityClass;

public class PreGameMenuViewImpl {

	public static void preGameMenuSetup(Stage primaryStage) {
		
		Parent root;
		SceneLauncherImpl launcher = new SceneLauncherImpl("PreGameScene.fxml");
		root = launcher.launchScene();
		UtilityClass.setScene(primaryStage, root);
		primaryStage.hide();
                primaryStage.setFullScreen(true);
                primaryStage.setFullScreen(false);
                primaryStage.show();
        
	}

}
