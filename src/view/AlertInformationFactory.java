package view;

import javafx.scene.control.Alert;

/**
 * Alessia Rocco 
 * An interface in order to create an Alert INformation view
 * message, in order to create a new alert with the features requested.
 */
public interface AlertInformationFactory {
    /**
     * 
     * @return the Alert Information requested.
     */
    Alert getAlert();
}
