package menu.view;

import java.io.IOException;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.stage.Stage;
import util.SceneLauncherImpl;
import util.UtilityClass;

public class MenuViews extends Application{
	
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

	public static void preGameMenuSetup(Stage primaryStage) {
	
		Parent root;
		SceneLauncherImpl launcher = new SceneLauncherImpl("PreGameScene.fxml");
		root = launcher.launchScene();
		UtilityClass.setScene(primaryStage, root);
		primaryStage.show();
	
	}
	
	static public void profileMenuSetup(Stage primaryStage) {
		
		Parent root;
		SceneLauncherImpl launcher = new SceneLauncherImpl("ProfileMenuScene.fxml");
		root = launcher.launchScene();
		UtilityClass.setScene(primaryStage, root);
        primaryStage.show();

	}
	
	public static void settingsMenuSetup(Stage primaryStage) {
		
		Parent root;
		SceneLauncherImpl launcher = new SceneLauncherImpl("SettingsMenuScene.fxml");
		root = launcher.launchScene();
		UtilityClass.setScene(primaryStage, root);
        primaryStage.show();
		
	}

}
