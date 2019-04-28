package menu.control;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import menu.view.MenuView;
import util.UtilityClass;
/**
 * This is an implementation of the Interface SettingsMenuControl.
 */
public class SettingsMenuControlImpl implements SettingsMenuControl {
    @FXML
    private CheckBox musicBox;
    @FXML
    private CheckBox voiceBox;
    @FXML
    private CheckBox soundFXBox;
    @FXML
    private CheckBox criccaBox;
    /**
     * {@inheritDoc}
     */
    public void backClicked(final ActionEvent event) {
        File settingsFilePath = new File(System.getProperty("user.dir") 
                                        + System.getProperty("file.separator") 
                                        + "res" + System.getProperty("file.separator") + "settings.txt");
        try {
            PrintWriter writer = new PrintWriter(settingsFilePath.getPath());
            if (criccaBox.isSelected()) {
                writer.write("points_for_cricca: TRUE\n");
            } else {
                writer.write("points_for_cricca: FALSE\n");
            }
            if (musicBox.isSelected()) {
                writer.write("music: TRUE\n");
            } else {
                writer.write("music: FALSE\n");
            }
            if (voiceBox.isSelected()) {
                writer.write("voices: TRUE\n");
            } else {
                writer.write("voices: FALSE\n");
            }
            if (soundFXBox.isSelected()) {
                writer.write("sfx: TRUE\n");
            } else {
                writer.write("sfx: FALSE\n");
            }
            writer.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error in writing into the Settings file.\nShutting down...");
        }
        MenuView.menuSetup(UtilityClass.returnStageOf(event), "MainMenuScene.fxml");
    }
    /**
     * {@inheritDoc}
     */
    public void initialize() {
        try {
        BufferedReader reader = new BufferedReader(new FileReader(System.getProperty("user.dir") 
                                                                    + System.getProperty("file.separator") 
                                                                    + "res" + System.getProperty("file.separator") + "settings.txt"));
        String line = reader.readLine();
        criccaBox.setSelected(line.equals("points_for_cricca: TRUE"));
        line = reader.readLine();
        musicBox.setSelected(line.equals("music: TRUE"));
        line = reader.readLine();
        voiceBox.setSelected(line.equals("voices: TRUE"));
        line = reader.readLine();
        soundFXBox.setSelected(line.equals("sfx: TRUE"));
        reader.close();
        } catch (IOException e) {
            System.out.println("Error in reading Settings file.\nShutting down...");
            System.exit(1);
        }
    }
}
