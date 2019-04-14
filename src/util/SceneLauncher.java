package util;

import javafx.scene.Parent;

/**
 * This is the Interface for an FXML file loader
 */
public interface SceneLauncher {
    /**
     * This method loads the FXML file previously set 
     * @return Parent 
     */
    Parent launchScene();
    /**
     * This is the Interface for an FXML file loader
     */
    void setSceneName(String newStringName);

}
