package sample;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;

/**
 * An display where information can be seen and edited all at once
 * This class handles the formatting of the GUI to be displayed
 * subclasses control the information to be displayed
 */
public abstract class EditableInfoDisplay {

    protected GridPane displayGridPane; //The Actual Background Grid which has been passed through to be displayed on

    protected Button backButton;

    protected GridPane centerGrid; //The grid placed at the center of the borderPane which will display most information

    protected BorderPane editableBorderPane; //The borderPane which will display everything (Will be placed inside displayGridPane

    protected Customiser customiser;

    protected Label titleLabel;

    protected List<Label> listOfBasicLabelTitleNodes; // A list by which basic Label Title Nodes can be stored to be placed on the grid

    protected List<Label> listOfBasicLabelInfoNodes; // A list by which basic Label Nodes can be stored to be placed on the grid

    protected List<TextField> listOfBasicTextFieldNodes; // A list by which basic Label Nodes can be stored to be placed on the grid

    private List<BorderPane> listOfBasicBorderPaneNodes;  // A list by which basic BorderPane Nodes can be stored to be placed on the grid
    
    protected CentralViewState backCentralViewStatePage; //A CentralViewState subclass page to go back to

    protected Account userAccount; //The account the user is using
   

    public EditableInfoDisplay(GridPane gridPaneToDisplayOn, CentralViewState statePageToGoBackTo, String title, Account userAccount) {

        customiser = Customiser.getInstance();
        this.userAccount = userAccount;

        titleLabel = new Label(title);
        titleLabel.setPadding(new Insets(0,0,0,20));
        customiser.customiseListOfNodesToFont("FiraGo", 35, titleLabel);

        displayGridPane = gridPaneToDisplayOn;



        backCentralViewStatePage = statePageToGoBackTo;

        backButton = new Button("Back");
        backButton.setOnAction(this::goBack);
        customiser.addLabelledNodeToDefaultCustomisation(backButton);

       // displayGridPane.getChildren().add(backButton);

        editableBorderPane = new BorderPane();

        centerGrid = new GridPane();
        centerGrid.setMinHeight(500);

        centerGrid.setHgap(5);

        listOfBasicLabelTitleNodes = new ArrayList<>();
        listOfBasicLabelInfoNodes = new ArrayList<>();
        listOfBasicTextFieldNodes = new ArrayList<>();
        listOfBasicBorderPaneNodes = new ArrayList<>();

    }

    private void goBack(ActionEvent event) {

        displayGridPane.getChildren().clear();

        backCentralViewStatePage.setGridPane(displayGridPane);

        backCentralViewStatePage.build();

      //  displayGridPane.getChildren().add(backCentralViewStatePage.getMainPane());
    }

    /**
     * Puts together the main structure of the editable page
     * To be called when all the basicInfoNodes have been added correctly
     */
    protected void buildEditableInfoDisplay() {
       if (listOfBasicBorderPaneNodes.size() != 0 && listOfBasicLabelTitleNodes.size() != 0) {

           editableBorderPane.setTop(titleLabel);

            editableBorderPane.setCenter(centerGrid);
            editableBorderPane.setRight(backButton);

            displayGridPane.getChildren().add(editableBorderPane);

            buildCenterGridBasicInfoNodes();


       } else {
           throw new IllegalArgumentException("All basic nodes must be filled before building GUI");
       }
    }

    /**
     * Method that places all the nodes
     * parallel to each other according to Label - EditableLabel
     * in a displayable form
     */
    protected void buildCenterGridBasicInfoNodes() {

        //if ((listOfBasicLabelTitleNodes.size() != listOfBasicBorderPaneNodes.size())) {
            //throw new ArithmeticException("both basic node lists must be equal to be displayed");
      //  } else {

           boolean isAdmin = userAccount instanceof AdministratorAccount;

           System.out.println(userAccount.getFirstName() + " Is admin: " + isAdmin);

            centerGrid.setPadding(new Insets(50, 50, 50, 50));
            centerGrid.setMinWidth(500);

            int labelColumn = 0;  int infoLabelColumn = 10;

            int padding = 10;

            int row = 0;

            for (Label l : listOfBasicLabelTitleNodes) {
                l.setPadding(new Insets(padding, padding, padding, padding));
                centerGrid.add(l, labelColumn, row);
                row++;
            }

            row = 0;

            if (isAdmin) {
                for (BorderPane bd : listOfBasicBorderPaneNodes) {
                    centerGrid.add(bd, infoLabelColumn, row);
                    row++;
                }

            } else {
                for (BorderPane bd : listOfBasicBorderPaneNodes) {
                    centerGrid.add(bd.getCenter(), infoLabelColumn, row);
                    row++;
                }
            }

            for (Label l : listOfBasicLabelInfoNodes) {
                centerGrid.add(l, infoLabelColumn, row);
                row++;
            }

    }

    /**
     * Adds a list of title labels to  a label list and customises to default
     * customisation
     * Must be entered in order of display
     * @param labels
     */
    protected void addTitleLabels(Label...labels) {
        for (Label l : labels) {
            customiser.addLabelledNodeToDefaultCustomisation(l);
            listOfBasicLabelTitleNodes.add(l);
        }
    }

    /**
     * Adds a list of info labels to  a label list and customises to default
     * customisation
     * Must be entered in order of display
     * @param labels
     */
    protected void addInfoLabels(Label...labels) {
        for (Label l : labels) {
            customiser.addLabelledNodeToDefaultCustomisation(l);
            listOfBasicLabelInfoNodes.add(l);
        }
    }

    /**
     * Adds a list of labels to  a label list and customises to default
     * customisation
     * Must be entered in order of display
     * @param textFields
     */
    protected void addTextFields(TextField...textFields) {
        for (TextField t : textFields) {
            customiser.addTextInputControlNodeToDefaultCustomisation(t);
            listOfBasicTextFieldNodes.add(t);
        }
    }

    /**
     * Adds a list of BorderPanes to  a label list
     * Must be entered in order of display
     * @param borderPanes
     */
    protected void addBorderPanes(BorderPane...borderPanes) {
        for (BorderPane bd : borderPanes) {
            listOfBasicBorderPaneNodes.add(bd);
        }
    }

    /**
     * Adds a list of EditableLabels to  a BorderPane list and customises to default
     * customisation
     * Must be entered in order of display
     * @param editableLabels
     */
    protected void addEditableLabels(EditableLabel...editableLabels) {

        customiser.addEditableLabeledNodesToDefaultCustomisation(editableLabels);

        for (EditableLabel eL : editableLabels) {
            addBorderPanes(eL.getEditablePane());
        }
    }



}
