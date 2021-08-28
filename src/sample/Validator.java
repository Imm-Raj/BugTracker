package sample;


import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.*;
import javafx.scene.control.TextField;

/**
 *
 */
public class Validator {

    ErrorDisplay errorDis;

    public Validator(ErrorDisplay ed) {
        errorDis = ed;
    }

    /**
     * validates a textField to make sure that it is not empty and that
     * a string has been entered
     *If there is no text, then there will a corresponding entry into the error display
     * object with the prompt text of the TextField  (used as a name of the field) and
     * mentions that it is empty
     * @param textFieldList
     * @return true if all the textfields in the list is not empty
     */
    public boolean validateForNonEmptyTextFieldsList(List<TextField> textFieldList) {

        for (TextField tf : textFieldList) {

            if (tf.getPromptText() == null) {
                throw new NullPointerException("There is no prompt text set for this text field \n cannot" +
                        " be validated");
            }

            if (tf.getText().trim().isEmpty()) {
                errorDis.addError(tf.getPromptText() + " is empty");
                return false;
            }
        }

        return true;
    }

    /**
     * validates whether there is a null value present in any of the
     * combo boxes for the date entry
     * @param dayB
     * @param monthB
     * @param yearB
     * @return
     */
    public boolean validateForNullDateOfBirth(ComboBox<Integer> dayB, ComboBox<Integer> monthB, ComboBox<Integer> yearB) {
        if (dayB.getValue() == null || monthB.getValue() == null || yearB.getValue() == null) {
            errorDis.addError("Date of Birth is not entered properly");
            return false;
        } else {
            return true;
        }
    }

    /**
     * Takes in a list of check boxes and validates them for at least one selection
     * @param checkBoxList
     * @return true if there is at least one selected
     * checkbox within the list, false otherwise
     */
    public boolean validateForAnyCheckBoxSelection(List<CheckBox> checkBoxList) {
        for (CheckBox acb : checkBoxList) {
            if (acb.isSelected()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Validates whether an input Strings' characters is above a given character limit
     * @param maxCharacterCount
     * @param inputString
     * @param textInputName
     * @return
     */
    public boolean validateIsAboveCharacterLimit(int maxCharacterCount, String inputString, String textInputName) {
        if (inputString.length() > maxCharacterCount) {

            int diff =  inputString.length()-maxCharacterCount;

            errorDis.addError("Max character Limit is " + maxCharacterCount + " for " + textInputName
            + "\n" + "limit exceeded by " + diff + " characters");
            return true;
        } else {
            return false;
        }

    }




}
