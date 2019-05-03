package util;

import java.awt.Toolkit;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
/**
 * This class contains various utility methods used throughout the project.
 */
public final class UtilityClass {
    private static final double ADJUSTED_SCREEN_WIDTH = 0.8 * Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    private static final double ADJUSTED_SCREEN_HEIGHT = 0.89 * Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    private static String profilesFilePath = "res" + System.getProperty("file.separator") + "profiles" + System.getProperty("file.separator");
    /**
     * This method returns the Stage on which an event occurred.
     * @param event - The event that occurred on the Stage to be found.
     * @return Stage - The Stage found
     */
    public static Stage returnStageOf(final ActionEvent event) {
        Stage stageFound = (Stage) ((Node) event.getSource()).getScene().getWindow();
        return stageFound;
    }
    /**
     * This method sets a Scene onto a Stage using specific proportions.
     * @param primaryStage - The Stage on which to set the Scene.
     * @param root - the parent of the Scene to be set.
     */
    public static void setScene(final Stage primaryStage, final Parent root) {
        primaryStage.setTitle("Beccaccino/Marafone");
        Scene newscene = new Scene(root, ADJUSTED_SCREEN_WIDTH, ADJUSTED_SCREEN_HEIGHT);
        primaryStage.setScene(newscene);
        primaryStage.sizeToScene();
        primaryStage.setResizable(false);
    }
    /**
     * This method sets a Scene onto a Stage using specific proportions.
     * @param profileName - the name of the new profile to be created.
     * @return false if the creation process is unsuccessful, true if successful.
     */
    public static boolean createProfile(final String profileName) {
        File file = new File(profilesFilePath + profileName + ".txt");
        if (file.exists()) {
            return false;
        } else {
            try {
                file.createNewFile();
            } catch (IOException e) {
                return false;
            }
            return true;
        }
    }
    /**
     * This method populates the ComboBox for one of the AIs with the possible difficulty choices.
     * @param comboBox - the name of the new profile to be created.
     */
    public static void populateAIComboBox(final ComboBox<String> comboBox) {
        comboBox.getItems().add("Basic AI");
        comboBox.getItems().add("Medium AI");
    }
    /**
     * This method checks whether or not the profiles folder and the settings file exist, and creates them if they don't.
     */
    public static void initialSetup() {
        File profilesPath = new File(System.getProperty("user.dir") + System.getProperty("file.separator") 
                                    + "res" + System.getProperty("file.separator") + "profiles");
        File settingsFilePath = new File(System.getProperty("user.dir") + System.getProperty("file.separator") 
        + "res" + System.getProperty("file.separator") + "settings.txt");
        if (!profilesPath.isDirectory()) {
            profilesPath.mkdirs();
        }
        if (!settingsFilePath.exists() || settingsFilePath.isDirectory()) {
            try {
                settingsFilePath.createNewFile();
            } catch (IOException e) {
                System.out.println("Cannot proceed without a Settings file\n Shutting down...");
                System.exit(1);
            }
            try {
                FileWriter writer = new FileWriter(settingsFilePath);
                writer.write("points_for_cricca: FALSE\n");
                writer.write("music: TRUE\n");
                writer.write("voices: TRUE\n");
                writer.write("sfx: TRUE");
                writer.close();
            } catch (IOException e) {
                System.out.println("A problem has occurred while writing in the Settings file\n Shutting down...");
                System.exit(1);
            }
        }
    }
    /**
     * This method sets the default background image on the borderpane passed.
     * @param borderPane - the BorderPane on which to set the default background image.
     */
    public static void setBackgroundImage(final BorderPane borderPane) {
        Image image = new Image("file:res" + System.getProperty("file.separator") + "images" + System.getProperty("file.separator") + "MenuBackground.jpg");
        BackgroundImage backgroundImage = new BackgroundImage(image,
                            BackgroundRepeat.NO_REPEAT,
                            BackgroundRepeat.NO_REPEAT,
                            BackgroundPosition.CENTER,
                            new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false));
        Background background = new Background(backgroundImage);
        borderPane.setBackground(background);
    }
    private UtilityClass() {
    }
}


