package util;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
/**
 * This is an implementation of SceneLauncher.
 */
public class SceneLauncherImpl implements SceneLauncher {
    private final FXMLLoader loader;
    private final String scenePath;
    private String sceneName;
    private Parent root;
    /**
     * Class Constructor.
     * @param passedSceneName - the name of the Scene to be loaded.
     */
    public SceneLauncherImpl(final String passedSceneName) {
        loader = new FXMLLoader();
        scenePath = "/scenes/";
        sceneName = passedSceneName;
    }
    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    public void setSceneName(final String newStringName) {
        sceneName = newStringName;
    }
}
