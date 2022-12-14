package application;

import menu.view.MenuView;
import util.UtilityClass;
import javafx.application.Application;
/**
 * The launcher containing the main method and the call to Application.launch.
 */
public final class Launcher {
    /**
     * This is the Main Method.
     * @param args - the standard args parameter.
     */
    public static void main(final String[] args) {
        UtilityClass.initialSetup();
        Application.launch(MenuView.class, args);
    }

    private Launcher() {
    }

}
