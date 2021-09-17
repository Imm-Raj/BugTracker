package sample;

import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.input.MouseEvent;

import java.util.List;

/**
 * A label that turns into a textField when the mouse hovers over it
 * can be used to edit information easily
 */
public class EditableLabel {

    private Label infoLabel;

    private TextField infoTextField;

    private String originalInformation;

    private String currentInformation;

    private BorderPane containerPane;

    public EditableLabel(String information) {

        originalInformation = information;
        currentInformation = originalInformation;

        infoTextField = new TextField(originalInformation);
        infoLabel = new Label(originalInformation);

        infoLabel.setPrefWidth(150);
        infoLabel.setWrapText(true);

        infoLabel.setMinHeight(infoTextField.getHeight());
        infoLabel.setMinWidth(infoTextField.getWidth());

        containerPane = new BorderPane(infoLabel);

        containerPane.setOnMouseEntered(this::makeEditable);
        containerPane.setOnMouseExited(this::makeUnEditable);

    }

    public BorderPane getEditablePane() {
        return containerPane;
    }

    public Label getInfoLabel() {
        return infoLabel;
    }

    /**
     * @return always returns current information
     */
    public String getCurrentInformation() {
        return currentInformation;
    }

    /**
     * converts the label into editable form so that the user can edit
     * This is done by using a textField containing the information
     * stored in the label
     * @param event
     */
    private void makeEditable(MouseEvent event) {

        containerPane.setCenter(infoTextField);

        System.out.println("Current Info = " + currentInformation);
    }

    /**
     * Makes the Label uneditable by converting a textField
     * back into a label
     * If no change is made to the original information then
     * The label is kept with original information
     */
    private void makeUnEditable(MouseEvent event) {

        //System.out.println("Make uneditable");

        boolean isEmpty = infoTextField.getText().trim().isEmpty();

        boolean equalsOriginalInfo = infoTextField.getText().equals(originalInformation);

        //System.out.println("Is empty: " + isEmpty);

        if (!isEmpty && !equalsOriginalInfo) {

            currentInformation = infoTextField.getText();

            infoLabel.setText(infoTextField.getText());

            containerPane.setCenter(infoLabel);

        } else {

            currentInformation = originalInformation;

            infoLabel.setText(originalInformation);
            infoTextField.setText(originalInformation);

            containerPane.setCenter(infoLabel);

        }
    }

    public void accept(EditableLabelCustomisationVisitor visitor) {
        visitor.visitEditableLabel(this, infoLabel, infoTextField);
    }

}
