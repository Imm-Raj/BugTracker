package sample;


import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;

public class EditableLabelCustomisationVisitor extends CustomisationVisitor {

    private  String defaultFont;
    private int defaultFontSize;

    public EditableLabelCustomisationVisitor(String defaultFont, int defaultFontSize) {
        this.defaultFont = defaultFont;
        this.defaultFontSize = defaultFontSize;
    }

    public void visitEditableLabel(EditableLabel eLabel, Label infoLabelToCustomise, TextField infoTextFieldToCustomise) {
        infoLabelToCustomise.setFont(new Font(defaultFont, defaultFontSize));
        infoTextFieldToCustomise.setFont(new Font(defaultFont, defaultFontSize));
    }


}
