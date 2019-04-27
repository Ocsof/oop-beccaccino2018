package util;

import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
/**
 * This class contains various utility methods used throughout the project.
 */
public final class UtilityClass {
    private static double adjustedScreenWidth = 1 / 3 * Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    private static double adjustedScreenHeight = 1 / 3 * Toolkit.getDefaultToolkit().getScreenSize().getHeight();
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
        Scene newscene = new Scene(root, adjustedScreenWidth, adjustedScreenHeight);
        primaryStage.setScene(newscene);
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
    private UtilityClass() {
    }
}


