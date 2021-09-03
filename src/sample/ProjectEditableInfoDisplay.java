package sample;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class ProjectEditableInfoDisplay extends EditableInfoDisplay {


    private Project project;

    private Label projectNameLabel;                     private EditableLabel projectNameEditable;
    private Label projectDescriptionLabel;              private EditableLabel projectDescriptionLabelEditable;
    private Label projectCreatedByLabel;                private Label projectCreatedByLabelInfo;
    private Label projectDateCreatedLabel;              private Label projectDateCreatedLabelInfo;
    private Label projectIDLabel;                       private Label projectIDInfo;

    private HBox centerHBox;

    private BorderPane rightBorderPane;

    private SearchableList ticketSearchableList;

    private Button saveButton;

    private List<CheckBox> listOfAllPossibleParticipantCheckBoxes;

    public ProjectEditableInfoDisplay(GridPane gridPaneToDisplayOn, CentralViewState statePageToGoBackTo, Project projectToDisplay, String title, Account userAccount) {
        super(gridPaneToDisplayOn, statePageToGoBackTo, title, userAccount);

        project = projectToDisplay;

        if (project == null) {
            throw new NullPointerException("Project is NUll cannot display in editable display");
        } else {
        projectNameLabel = new Label("Name: ");                projectNameEditable = new EditableLabel(project.getName());
        projectDescriptionLabel = new Label("Description: ");  projectDescriptionLabelEditable = new EditableLabel(project.getDescription());
        projectCreatedByLabel = new Label("Created By: ");     projectCreatedByLabelInfo = new Label(project.getCreatedBy().getFirstName() + " " +  project.getCreatedBy().getLastName());
        projectDateCreatedLabel = new Label("Date Created: "); projectDateCreatedLabelInfo = new Label(project.getDateCreated().toString());
        projectIDLabel = new Label("Project ID: ");            projectIDInfo = new Label(project.getProjectID());

        ProjectExtractor projectExtractor = new ProjectExtractor(project);

        saveButton = new Button("Save");
        //saveButton.setPadding(new Insets(0, 0,0,5));
        customiser.addLabelledNodeToDefaultCustomisation(saveButton);

        ticketSearchableList = new SearchableList(projectExtractor.getDisplayCompOfAllTicketsInProject(), "Tickets ", 500,485);

        addTitleLabels(projectNameLabel, projectDescriptionLabel,projectCreatedByLabel, projectDateCreatedLabel, projectIDLabel);
        addEditableLabels(projectNameEditable, projectDescriptionLabelEditable);
        addInfoLabels(projectCreatedByLabelInfo, projectDateCreatedLabelInfo, projectIDInfo);

        centerHBox = new HBox();

        rightBorderPane = new BorderPane();

        buildEditableInfoDisplay();

        editableBorderPane.setCenter(centerHBox);

        editableBorderPane.setRight(rightBorderPane);

        rightBorderPane.setTop(backButton);
        rightBorderPane.setBottom(saveButton);

        if (!(userAccount instanceof AdministratorAccount)) {
            saveButton.setVisible(false);
        }

        saveButton.setOnAction(this::saveEditedProject);

        centerHBox.getChildren().addAll(centerGrid, ticketSearchableList.getMainPane());

        listOfAllPossibleParticipantCheckBoxes = new ArrayList<>();


        fillParticipantCheckBoxes();

        }

    }


    private void buildParticipantCheckBoxes() {

        Label participantsLabel = new Label("Participants: ");
        participantsLabel.setPadding(new Insets(10,0,10,10));

        customiser.addLabelledNodeToDefaultCustomisation(participantsLabel);

        centerGrid.add(participantsLabel, 0, 19);


        int col = 0; int row = 20;

        for (CheckBox c : listOfAllPossibleParticipantCheckBoxes) {

            centerGrid.add(c, col, row);

            if (col == 1) {
                col = 0;
                row++;
            } else {
                col++;
            }

        }
    }

    /**
     * fills the information for participant checkboxes for updating
     */
    private void fillParticipantCheckBoxes() {


        AllCreatedAccounts ac = AllCreatedAccounts.getInstance();

        for (Account acc : ac.getAccountsList()) {

            CheckBox ch = new CheckBox(acc.getFirstName() + " " + acc.getLastName());

            ch.setPadding(new Insets(0,10,0,10));

            ch.setId(acc.getUserName());

            customiser.addCheckBoxNodeToDefaultCustomisation(ch);

            if (project.checkIfNameIsParticipantOfProject(acc.getUserName())) {
                ch.setSelected(true);
            }
            listOfAllPossibleParticipantCheckBoxes.add(ch);

        }

        buildParticipantCheckBoxes();

    }

    private void saveEditedProject(ActionEvent actionEvent) {

    }






}
