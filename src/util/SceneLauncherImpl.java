package util;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class SceneLauncherImpl implements SceneLauncher {
	
	FXMLLoader loader;
	String scenePath;
	String sceneName;
	
	public SceneLauncherImpl(String passedSceneName){
		
		loader = new FXMLLoader();
		scenePath = "/scenes/";
		sceneName = passedSceneName;
		
	}
	
	@Override
	public Parent launchScene() {
		
		loader.setLocation((this.getClass().getResource("/scenes/" + sceneName)));
		
		Parent root = null;
		
		try {
			root = loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
		    System.out.println("An Error Has Occurred While Attempting To Load An FXML Scene \nShutting Down...");
			System.exit(1);
		}
		return root;
		
	}

	@Override
	public void setSceneName(String newStringName) {
		
		sceneName = newStringName;
		
	}
}
