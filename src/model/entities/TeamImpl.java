package model.entities;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Alessia Rocco Team implementation.
 */
public class TeamImpl implements Team {
    private String teamName;
    private List<Player> team;
    private List<ItalianCard> cards;
    private int points;

    /**
     * Class constructor.
     * 
     * @param teamName the name of the team
     * @param team the team
     */
    public TeamImpl(final String teamName, final List<Player> team) {
        this.teamName = teamName;
        this.team = team;
        this.cards = new LinkedList<>();
        this.points = 0;
    }

    /**
     * {@inheritDoc}
     */
    public boolean addPlayer(final Player player) {
        return this.team.add(player);
    }

    /**
     * {@inheritDoc}
     */
    public List<Player> getPlayers() {
        return Collections.unmodifiableList(this.team);
    }

    /**
     * {@inheritDoc}
     */
    public void addWonCard(final ItalianCard card) {
        this.cards.add(card);
    }

    /**
     * {@inheritDoc}
     */
    public List<ItalianCard> getWonCards() {
        return Collections.unmodifiableList(cards);
    }

    /**
     * {@inheritDoc}
     */
    public void assignPoints(final int points) {
        this.points = this.points + points;
    }

    /**
     * {@inheritDoc}
     */
    public int getPoints() {
        return this.points;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((cards == null) ? 0 : cards.hashCode());
        result = prime * result + points;
        result = prime * result + ((team == null) ? 0 : team.hashCode());
        result = prime * result + ((teamName == null) ? 0 : teamName.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object team) {
        if (this == team) {
            return true;
        }
        if (team == null) {
            return false;
        }
        if (getClass() != team.getClass()) {
            return false;
        }
        TeamImpl other = (TeamImpl) team;
        if (cards == null) {
            if (other.cards != null) {
                return false;
            }
        } else if (!cards.equals(other.cards)) {
            return false;
        }
        if (points != other.points) {
            return false;
        }
        if (!team.equals(other.team)) {
            return false;
        }
        if (teamName == null) {
            if (other.teamName != null) {
                return false;
            }
        } else if (!teamName.equals(other.teamName)) {
            return false;
        }
        return true;
    }
}
