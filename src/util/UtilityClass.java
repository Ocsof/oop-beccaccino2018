package util;

import java.awt.Toolkit;
import java.io.File;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class UtilityClass {
	
	private static double adjustedScreenWidth = 1/3 * Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	private static double adjustedScreenHeight = 1/3 * Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	
	public static Stage returnStageOf(ActionEvent event) {
		
		Stage stageFound = (Stage) ((Node)event.getSource()).getScene().getWindow();
		return stageFound;
		
	}
	
	public static void setScene(Stage primaryStage, Parent root) {
		
		Scene newscene = new Scene(root, adjustedScreenWidth, adjustedScreenHeight);
		primaryStage.setScene(newscene);
		
	}
	
	public static void setupComboBoxes(ComboBox<String> box1,ComboBox<String> box2,ComboBox<String> box3,ComboBox<String> box4) {
		
		
		
	}
	
	public static void trialMethodForReadingFiles(){
		
		
		
	}
	
}


