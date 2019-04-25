package menu.view;

import java.io.IOException;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.stage.Stage;
import util.SceneLauncherImpl;
import util.UtilityClass;
/**
 * This Class is responsible for the overriding of the Application.start method and the creation 
 * and setup of the Stage for all Menu Scenes.
 */
public class MenuView extends Application {
    @Override
    public void start(final Stage primaryStage) throws IOException {
        menuSetup(primaryStage, "MainMenuScene.fxml");
    }
    /**
     * This method loads and sets every Menu Scene onto the Stage, also making sure visuals are updated properly.
     * @param primaryStage - the Stage on which to set the Scene.
     * @param sceneName -the name of he Scene to be loaded and set onto the Stage.
     */
    public static void menuSetup(final Stage primaryStage, final String sceneName) {
        Parent root;
        SceneLauncherImpl launcher = new SceneLauncherImpl(sceneName);
        root = launcher.launchScene();
        UtilityClass.setScene(primaryStage, root);
        primaryStage.hide();
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreen(false);
        primaryStage.show();
    }

}
