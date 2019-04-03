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
