package model.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import model.artificialIntelligence.AI;
import model.artificialIntelligence.AIImpl;
import model.artificialIntelligence.GameBasicAnalyzer;
import model.entities.BeccaccinoHand;
import model.entities.Hand;
import model.entities.Player;
import model.entities.PlayerImpl;
import model.entities.Team;
import model.entities.TeamImpl;
import view.GameViewImpl;

/**
 * The implementation of the Interface Ruleset.
 */
public class RulesetImpl implements Ruleset {

    /**
     * {@inheritDoc}
     */
    public Game newGame(final List<Player> players) {
        TurnOrder turnOrder = new BasicTurnOrder(players);
        List<Team> teams = createTeams(players);
        Game game = new BeccaccinoGame(turnOrder, teams.get(0), teams.get(1));
        return game;
    }

    /**
     * {@inheritDoc}
     */
    public GameViewImpl newGameView(final Game game) {
        GameViewImpl gameView = new GameViewImpl(game);
        return gameView;
    }

    /**
     * {@inheritDoc}
     */
    public Player newPlayer(final String name) {
        Hand hand = new BeccaccinoHand();
        Player player = new PlayerImpl(name, hand);
        return player;
    }

    /**
     * {@inheritDoc}
     */
    public Optional<AI> newAI(final Player player) {
        //QUANDO PRONTA QUI VA MESSA LA DISCRIMINAZIONE DELLA DIFFICOLTA' IN BASE ALLE OPZIONI DEL MENU
        GameBasicAnalyzer analyzer = new GameBasicAnalyzer(player.getHand().getCards());
        Optional<AI> ai = Optional.of(new AIImpl(player, analyzer));
        return ai;
    }
    /**
     * This method creates two teams from a list of 4.
     * @param playerList - The list of player to be divided.
     * @return teams
     */
    private List<Team> createTeams(final List<Player> playerList) {
        final List<Team> teams = new ArrayList<>();
        final Team team1 = new TeamImpl();
        final Team team2 = new TeamImpl();
        for (int i = 0; i < playerList.size(); i++) {
            if (i % 2 == 0) {
                team1.addPlayer(playerList.get(i));
            } else {
                team2.addPlayer(playerList.get(i));
            }
        }
        teams.add(team1);
        teams.add(team2);
        return teams;
    }

}
