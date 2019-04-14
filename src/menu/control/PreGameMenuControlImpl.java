package menu.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import controller.game.GameController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import menu.view.MainMenuView;
import model.entities.AI;
import model.entities.Player;
import model.logic.Game;
import model.logic.Ruleset;
import util.UtilityClass;
import view.GameView;
/**
 * This is an Implementation of the Interface PreGameMenuControl.
 */
public class PreGameMenuControlImpl implements PreGameMenuControl {
    @FXML
    private ComboBox<String> boxPlayer1;
    @FXML
    private ComboBox<String> boxPlayer2;
    @FXML
    private ComboBox<String> boxPlayer3;
    @FXML
    private ComboBox<String> boxPlayer4;

    /**
     * {@inheritDoc}
     */
    public void backClicked(final ActionEvent event) {
        MainMenuView.mainMenuSetup(UtilityClass.returnStageOf(event));
        }
    /**
     * {@inheritDoc}
     */
    public void startClicked(final ActionEvent event) {
        Ruleset ruleset = null;
        List<Player> playerList = new ArrayList<Player>();
        playerList.add(ruleset.newPlayer("Player1"));
        playerList.add(ruleset.newPlayer("Player2"));
        playerList.add(ruleset.newPlayer("Player3"));
        playerList.add(ruleset.newPlayer("Player4"));
        Game currentGame = ruleset.newGame(playerList);
        GameView currentGameView = ruleset.newGameView(currentGame);
        Map<Player, Optional<AI>> playerMap = new HashMap<Player, Optional<AI>>();
        playerMap.put(playerList.get(0), null);
        playerMap.put(playerList.get(1), ruleset.newAI("easy"));
        playerMap.put(playerList.get(2), ruleset.newAI("easy"));
        playerMap.put(playerList.get(3), ruleset.newAI("easy"));
        GameController currentGameController = new GameController(playerMap, currentGame, currentGameView);
    }

}
