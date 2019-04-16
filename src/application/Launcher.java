package application;

import menu.view.MainMenuView;
import javafx.application.Application;
/**
 * The launcher containing the main method and the call to Application.launch.
 */
public final class Launcher {
    /**
     * Main method.
     *
     * @param args
     *            args
     */
    public static void main(final String[] args) {
        Application.launch(MainMenuView.class, args);
    }

    private Launcher() {
    }

}
