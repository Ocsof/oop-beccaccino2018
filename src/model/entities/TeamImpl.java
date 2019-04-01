package model.entities;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
/**
 * Alessia Rocco
 * Team implementation.
 */
public class TeamImpl implements Team {
    private String teamName;
    private List<Player> team;
    private List<ItalianCard> cards;
    private int points;

    public TeamImpl(final String teamName, List<Player> team) {
        this.teamName = teamName;
        this.team = team;
        this.cards = new LinkedList<>();
        this.points = 0;
    }

    @Override
    public boolean addPlayer(final Player player) {
        return this.team.add(player);
    }

    @Override
    public List<Player> getPlayers() {
        return Collections.unmodifiableList(this.team);
    }

    @Override
    public void addWonCard(final ItalianCard card) {
        this.cards.add(card);
    }

    @Override
    public List<ItalianCard> getWonCards() {
        return Collections.unmodifiableList(cards);
    }

    @Override
    public void assignPoints(final int points) {
        this.points = this.points + points;
    }

    @Override
    public int getPoints() {
        return this.points;
    }

}
