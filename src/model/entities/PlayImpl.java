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
     * Class Constructor.
     */
    public PlayImpl() {
    }

    /**
     * setter of the card.
     * @param card an ItalianCard
     */
    public void setCardPlayed(final ItalianCard card) {
        this.card = card;
    }

    /**
     * setter of the message.
     * @param message the optional message
     */
    public void setMessage(final Optional<String> message) {
        if (message.isPresent()) {
            this.message = Optional.of(message).get();
        }
    }

    @Override
    public ItalianCard getCard() {
        return this.card;
    }

    @Override
    public Optional<String> getMessage() {
        if (message.isPresent()) {
            return Optional.of(this.message).get();
        }
        return Optional.empty();
    }

}
