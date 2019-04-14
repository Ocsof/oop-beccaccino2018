package model.entities;

import java.util.Optional;
/**
 * Alessia Rocco
 * Play Implementation.
 */
public class PlayImpl implements Play {
    private ItalianCard card;
    private Optional<String> message;
    /**
     * Class constructor.
     * @param card the card has been played
     * @param message the eventually message thrown with the card
     */
    public PlayImpl(final ItalianCard card, final Optional<String> message) {
        this.card = card;
        if (message.isPresent()) {
            this.message = message;
        }
    }
    /**
     * {@inheritDoc}
     */
    public ItalianCard getCard() {
        return this.card;
    }
    /**
     * {@inheritDoc}
     */
    public Optional<String> getMessage() {
        if (message.isPresent()) {
            return Optional.of(this.message).get();
        }
        return Optional.empty();
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((card == null) ? 0 : card.hashCode());
        result = prime * result + ((message == null) ? 0 : message.hashCode());
        return result;
    }
    @Override
    public boolean equals(final Object play) {
        if (this == play) {
            return true;
        }
        if (play == null) {
            return false;
        }
        if (getClass() != play.getClass()) {
            return false;
        }
        PlayImpl other = (PlayImpl) play;
        if (card == null) {
            if (other.card != null) {
                return false;
            }
        } else if (!card.equals(other.card)) {
            return false;
        }
        if (message == null) {
            if (other.message != null) {
                return false;
            }
        } else if (!message.equals(other.message)) {
            return false;
        }
        return true;
    }
}
