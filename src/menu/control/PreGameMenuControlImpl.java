package menu.control;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import controller.game.GameController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import menu.view.MenuView;
import model.artificialIntelligence.AI;
import model.entities.Player;
import model.logic.Game;
import model.logic.Ruleset;
import model.logic.RulesetImpl;
import util.UtilityClass;
import view.GameViewImpl;
/**
 * This is an Implementation of the Interface PreGameMenuControl.
 */
public class PreGameMenuControlImpl implements PreGameMenuControl {
    @FXML
    private ComboBox<String> profilesComboBox;
    @FXML
    private ComboBox<String> leftmostAIComboBox;
    @FXML
    private ComboBox<String> upperAIComboBox;
    @FXML
    private ComboBox<String> rightmostAIComboBox;
    /**
     * {@inheritDoc}
     */
    public void initialize() {
        File folder = new File("res/profiles");
        File[] files = folder.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                profilesComboBox.getItems().add(files[i].getName().replaceFirst("[.][^.]+$", ""));
            }
        }
        UtilityClass.populateAIComboBox(rightmostAIComboBox);
        UtilityClass.populateAIComboBox(upperAIComboBox);
        UtilityClass.populateAIComboBox(leftmostAIComboBox);
    }
    /**
     * {@inheritDoc}
     */
    public void startClicked(final ActionEvent event) {
        if (profilesComboBox.getValue() == null) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Profile not Selected");
            alert.setContentText("A profile has to be selected in order to play a match.\nYou can create profiles in the Create new Profile section of the Main Menu.");
            alert.showAndWait();
        } else {
            Ruleset ruleset = new RulesetImpl();
            List<Player> playerList = new ArrayList<Player>();
            playerList.add(ruleset.newPlayer(profilesComboBox.getValue()));
            if (rightmostAIComboBox.getValue() == null) {
                playerList.add(ruleset.newPlayer("Basic AI 1"));
            } else {
                playerList.add(ruleset.newPlayer("Medium AI 1"));
            }
            if (upperAIComboBox.getValue() == null) {
                playerList.add(ruleset.newPlayer("Basic AI 2"));
            } else {
                playerList.add(ruleset.newPlayer("Medium AI 2"));
            }
            if (leftmostAIComboBox.getValue() == null) {
                playerList.add(ruleset.newPlayer("Basic AI 3"));
            } else {
                playerList.add(ruleset.newPlayer("Medium AI 3"));
            }
            Game currentGame = ruleset.newGame(playerList);
            GameViewImpl currentGameView = ruleset.newGameView(currentGame, UtilityClass.returnStageOf(event));
            Map<Player, Optional<AI>> playerMap = new HashMap<Player, Optional<AI>>();
            playerMap.put(playerList.get(0), Optional.empty());
            playerMap.put(playerList.get(1), ruleset.newAI(playerList.get(1), rightmostAIComboBox.getValue()));
            playerMap.put(playerList.get(2), ruleset.newAI(playerList.get(2), upperAIComboBox.getValue()));
            playerMap.put(playerList.get(3), ruleset.newAI(playerList.get(3), leftmostAIComboBox.getValue()));
            GameController currentGameController = new GameController(playerMap, currentGame, currentGameView);
            currentGameView.setController(currentGameController);
        }
    }
    /**
     * {@inheritDoc}
     */
    public void backClicked(final ActionEvent event) {
        MenuView.menuSetup(UtilityClass.returnStageOf(event), "MainMenuScene.fxml");
    }
}
