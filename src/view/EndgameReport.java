package view;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import menu.view.MenuView;

import model.entities.Team;

/**
 * Info alert about game results.
 */
public class EndgameReport {
        /**
     * Launch an info alert with information about game results.
     * 
     * @param primaryStage - the stage where this alert has to appear.
     * @param winningTeam - the winning team.
     * @param losingTeam - the losing team.
     */
    public EndgameReport(final Stage primaryStage, final Team winningTeam, final Team losingTeam) {
        final int winningTeamPoints = winningTeam.getPoints() / 3;
        final int losingTeamPoints = losingTeam.getPoints() / 3;
        final Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Game over");
        alert.setHeaderText("Here is the results");
        alert.setContentText("Winning players are: " + winningTeam.getPlayers().get(0) + " and "
                + winningTeam.getPlayers().get(0) + "./nThey totalised " + winningTeamPoints
                + " points./nLosing players are: " + losingTeam.getPlayers().get(0) + " and "
                + losingTeam.getPlayers().get(0) + "./nThey totalised" + losingTeamPoints + " points.");
        alert.showAndWait();
        MenuView.menuSetup(primaryStage, "MainMenuScene.fxml");

    }
}



