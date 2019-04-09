package menu.view;

import java.io.IOException;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.stage.Stage;
import util.SceneLauncherImpl;
import util.UtilityClass;

public class MainMenuView extends Application{
	
	@Override
	public void start(Stage primaryStage) throws IOException {
		
		mainMenuSetup(primaryStage);
	
	}
	
	public static void mainMenuSetup(Stage primaryStage) {
		
	        Parent root;
		SceneLauncherImpl launcher = new SceneLauncherImpl("MainMenuScene.fxml");
		root = launcher.launchScene();
		UtilityClass.setScene(primaryStage, root);
                primaryStage.show();
        
	}

}
