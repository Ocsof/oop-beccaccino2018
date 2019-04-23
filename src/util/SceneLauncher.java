package util;

import javafx.scene.Parent;

/**
 * This is the Interface for an FXML file loader.
 */
public interface SceneLauncher {
    /**
     * This method loads the FXML file previously set.
     * @return Parent 
     */
    Parent launchScene();
    /**
     * This method sets the name of the Scene to be loaded.
     * Created specifically in case a Launcher has ti be used more than once to load a Scene.
     * @param newStringName - the name of the scene to be loaded.
     */
    void setSceneName(String newStringName);

}
