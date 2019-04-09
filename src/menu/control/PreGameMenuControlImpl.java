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
import model.entities.PlayerImpl;
import model.logic.Game;
import model.logic.Ruleset;
import util.UtilityClass;
import view.GameView;
import view.GameViewImpl;

public class PreGameMenuControlImpl implements PreGameMenuControl {
	
	@FXML
	private ComboBox<String> boxPlayer1;
	@FXML
	private ComboBox<String> boxPlayer2;
	@FXML
	private ComboBox<String> boxPlayer3;
	@FXML
	private ComboBox<String> boxPlayer4;

	@Override
	public void backClicked(ActionEvent event) {
		
		MainMenuView.mainMenuSetup(UtilityClass.returnStageOf(event));

	}

	@Override
	public void startClicked(ActionEvent event) {
		//chiedi ad alessia per vedere cosa passare e cosa no
		Ruleset ruleset = null;
		List<Player> playerList = new ArrayList<Player>();
		playerList.add(ruleset.newPlayer("Player1"));
		playerList.add(ruleset.newPlayer("Player2"));
		playerList.add(ruleset.newPlayer("Player3"));
		playerList.add(ruleset.newPlayer("Player4"));
		Game currentGame = ruleset.newGame(playerList);
		GameView currentGameView = ruleset.newGameView(currentGame);
		Map<Player, Optional<AI>> playerMap = new HashMap<Player, Optional<AI>>();
		playerMap.put(playerList.get(0),null);
		playerMap.put(playerList.get(1),null);
		playerMap.put(playerList.get(2),null);
		playerMap.put(playerList.get(3),null);
		GameController currentGameController = new GameController(playerMap,currentGame,currentGameView);
;	}

}
