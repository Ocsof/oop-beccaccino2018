package menu.control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.TextField;

import menu.view.MenuView;
import util.UtilityClass;
/**
 * This is an implementation of the Interface ProfileMenuControl.
 */
public class ProfileMenuControl {
    @FXML
    private BorderPane borderPane;
    @FXML
    private TextField nameField;
    /**
     * {@inheritDoc}
     */
    public void initialize() {
        UtilityClass util = new UtilityClass();
        util.setBackgroundImage(borderPane);
    }
    /**
     * {@inheritDoc}
     */
    public void backClicked(final ActionEvent event) {
        MenuView.menuSetup(UtilityClass.returnStageOf(event), "MainMenuScene.fxml");
    }
    /**
     * {@inheritDoc}
     */
    public void createClicked(final ActionEvent event) {
        if (nameField.getText().isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Empty Profile Name");
            alert.setContentText("You can't create a profile with an empty name.");
            alert.showAndWait();
        } else if (!UtilityClass.createProfile(nameField.getText())) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Name");
            alert.setContentText("A profile with this name already exists or the name chosen contains invalid characters.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Profile Created");
            alert.setHeaderText(null);
            alert.setContentText("The profile was created successfully.");
            alert.showAndWait();
        }
        nameField.setText("");
    }
}
