package application;

import javafx.application.Application;
import view.GameViewImpl;

/**
 * Alessia Rocco
 *Launch application.
 */
public final class Launcher {
    /**
     * Main: launch the GameView.
     * @param args
     *            args
     */
    public static void main(final String[] args) {
        Application.launch(MainMenuViewImpl.class, args);
    }

    private Launcher() {
    }
}
