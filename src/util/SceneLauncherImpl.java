package util;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class SceneLauncherImpl implements SceneLauncher {
	
	private final FXMLLoader loader;
	private final String scenePath;
	String sceneName;
	Parent root;
	
	public SceneLauncherImpl(String passedSceneName){
		
		loader = new FXMLLoader();
		scenePath = "/scenes/";
		sceneName = passedSceneName;
		
	}
	
	@Override
	public Parent launchScene() {
		loader.setLocation(this.getClass().getResource(scenePath + sceneName));
		
		try {
			root = loader.load();
		} catch (IOException e) {
		    System.out.println("An Error Has Occurred While Attempting To Load An FXML Scene \nShutting Down...");
			System.exit(1);
		}
		return root;
		
	}

	@Override
	public void setSceneName(final String newStringName) {
	    sceneName = newStringName;
	}
}
