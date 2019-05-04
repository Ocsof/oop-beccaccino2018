package view;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

/**
 * Alessia Rocco AlertInformationFactory implmentation.
 */
public class AlertInformationFactoryImpl implements AlertInformationFactory {
    private String title;
    private String headerText;
    private String contentText;
    private Stage primaryStage;
    private Alert alert = new Alert(AlertType.INFORMATION);

    /**
     * Class constructor.
     * 
     * @param title the title of the Alert
     * @param headerText the Header of the Alert
     * @param contentText the text in the Alert
     * @param primaryStage the stage that the Alert will be connected to.
     */
    public AlertInformationFactoryImpl(final String title, final String headerText, final String contentText,
            final Stage primaryStage) {
        this.title = title;
        this.headerText = headerText;
        this.contentText = contentText;
        this.primaryStage = primaryStage;
    }

    /**
     * {@inheritDoc}
     */
    public Alert getAlert() {
        alert.initOwner(this.primaryStage);
        alert.setTitle(this.title);
        alert.setHeaderText(this.headerText);
        alert.setContentText(this.contentText);
        return this.alert;
    }
}
