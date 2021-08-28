package sample;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * A simple GUI component that can be reused multiple times to display errors regarding
 * user input
 */
public class ErrorDisplay {

    private VBox errorPane;


    public ErrorDisplay() {
       errorPane = new VBox();
    }

    public VBox getErrorPane() {
        return errorPane;
    }

    public void addError(String errorDesc) throws IllegalArgumentException {

        Label errorLabel = new Label(errorDesc);
        errorLabel.setTextFill(Color.RED);
        errorPane.getChildren().add(errorLabel);

    }

    public void clearErrorPane() {
        errorPane.getChildren().clear();
    }

    public void displaySuccessMessage(String reasonForSuccess) {
        clearErrorPane();
        Label successLabel = new Label(reasonForSuccess + " Successful");
        successLabel.setTextFill(Color.GREEN);
        errorPane.getChildren().add(successLabel);
    }



}
