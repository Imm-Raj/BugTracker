package sample;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProjectEditableInfoDisplay extends EditableInfoDisplay {

    private Project project;


    protected CentralViewState backCentralViewStatePage; //A CentralViewState subclass page to go back to

    private Label projectNameLabel;                     private EditableLabel projectNameEditable;
    private Label projectDescriptionLabel;              private EditableLabel projectDescriptionLabelEditable;
    private Label projectCreatedByLabel;                private Label projectCreatedByLabelInfo;
    private Label projectDateCreatedLabel;              private Label projectDateCreatedLabelInfo;
    private Label projectIDLabel;                       private Label projectIDInfo;

    private HBox centerHBox;

    private BorderPane rightBorderPane;

    private VBox rightTopVbox;

    private  VBox rightBottomVBox;

    private SearchableList ticketSearchableList;

    private Button saveButton;

    private Button createTicketButton;

    private Button closeButton;

    private ErrorDisplay errorDisplay;

    private Validator validator;

    private List<CheckBox> listOfAllPossibleParticipantCheckBoxes;
    private List<Account> allSelectedParticipantAccounts;

    private TicketDisplayingVisitor ticketDisplayingVisitor;

    public ProjectEditableInfoDisplay(GridPane gridPaneToDisplayOn, CentralViewState statePageToGoBackTo, Project projectToDisplay, String title, Account userAccount) {

        super(gridPaneToDisplayOn, title, userAccount);

        project = projectToDisplay;

        backCentralViewStatePage = statePageToGoBackTo;


        errorDisplay = new ErrorDisplay();

        validator = new Validator(errorDisplay);

        isAccountAnEditor = userAccount instanceof AdministratorAccount || userAccount == project.getCreatedBy();

        System.out.println(userAccount.getFirstName()  + " " + isAccountAnEditor);

        if (project == null) {
            throw new NullPointerException("Project is NUll cannot display in editable display");
        } else {
            projectNameLabel = new Label("Name: ");                projectNameEditable = new EditableLabel(project.getName());
            projectDescriptionLabel = new Label("Description: ");  projectDescriptionLabelEditable = new EditableLabel(project.getDescription());
            projectCreatedByLabel = new Label("Created By: ");     projectCreatedByLabelInfo = new Label(project.getCreatedBy().getFirstName() + " " +  project.getCreatedBy().getLastName());
            projectDateCreatedLabel = new Label("Date Created: "); projectDateCreatedLabelInfo = new Label(project.getDateCreated().toString());
            projectIDLabel = new Label("Project ID: ");            projectIDInfo = new Label(project.getProjectID());



            saveButton = new Button("Save");
            //saveButton.setPadding(new Insets(0, 0,0,5));
            closeButton = new Button("Delete");

            backButton.setOnAction(e -> goBack());

            createTicketButton = new Button("+Ticket");

            customiser.addLabelledNodeToDefaultCustomisation(saveButton, closeButton, createTicketButton);

            ticketDisplayingVisitor = new TicketDisplayingVisitor(displayGridPane, userAccount, false);


            populateTicketSearchableList();



            processLabeledInfo();

            centerHBox = new HBox();

            rightBorderPane = new BorderPane();

            rightTopVbox = new VBox();
            rightBottomVBox = new VBox();


            buildEditableInfoDisplay();

            buildProjectSpecificEditableInfoDisplay();

        }

    }

    private void populateTicketSearchableList() {
        ProjectExtractor projectExtractor = new ProjectExtractor(project);

        List<Closable> listOfDisplayCompTickets = projectExtractor.getDisplayCompOfAllTicketsAssignedToAccountWithinProject(userAccount);

        for (Closable c : listOfDisplayCompTickets) {
            DisplayCompTicket displayCompTicket = (DisplayCompTicket) c;

            displayCompTicket.acceptTicketDisplayingVisitor(ticketDisplayingVisitor);
        }

        ticketSearchableList = new SearchableList(listOfDisplayCompTickets, "Tickets ", 500,485);
    }

    protected void goBack() {

        displayGridPane.getChildren().clear();

        backCentralViewStatePage.setGridPane(displayGridPane);

        backCentralViewStatePage.build();

        //  displayGridPane.getChildren().add(backCentralViewStatePage.getMainPane());
    }

    private void processLabeledInfo() {

        addTitleLabels(projectNameLabel, projectDescriptionLabel,projectCreatedByLabel, projectDateCreatedLabel, projectIDLabel);

        if (isAccountAnEditor) {

            addEditableLabels(projectNameEditable, projectDescriptionLabelEditable);
            addInfoLabels(projectCreatedByLabelInfo, projectDateCreatedLabelInfo, projectIDInfo);
        } else {

            addInfoLabels(projectNameEditable.getInfoLabel(), projectDescriptionLabelEditable.getInfoLabel(), projectCreatedByLabelInfo, projectDateCreatedLabelInfo, projectIDInfo);
        }

    }

    private void buildProjectSpecificEditableInfoDisplay() {

        editableBorderPane.setCenter(centerHBox);

        editableBorderPane.setRight(rightBorderPane);

        rightBorderPane.setTop(rightTopVbox);
        rightBorderPane.setBottom(rightBottomVBox);

        rightTopVbox.getChildren().addAll(backButton, createTicketButton);

        rightTopVbox.setSpacing(10);
        rightBottomVBox.setSpacing(10);

        rightBottomVBox.getChildren().addAll(closeButton, saveButton);

        if (!isAccountAnEditor) {
            closeButton.setVisible(false);
            saveButton.setVisible(false);
        }

        saveButton.setOnAction(this::saveEditedProject);
        closeButton.setOnAction(this::closeProject);
        createTicketButton.setOnAction(this::openCreateTicketPage);

        centerHBox.getChildren().addAll(centerGrid, ticketSearchableList.getMainPane());

        listOfAllPossibleParticipantCheckBoxes = new ArrayList<>();

        centerGrid.add(errorDisplay.getErrorPane(),10,25);


        fillParticipantCheckBoxes();
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

    private boolean validateAllInputs() {
        errorDisplay.clearErrorPane();

        if (projectNameEditable.getCurrentInformation() == "") {
            errorDisplay.addError("Please Enter project name");
            return false;
        }

        if (projectDescriptionLabelEditable.getCurrentInformation() == "") {
            errorDisplay.addError("Please Enter project description");
            return false;
        }

        List<Account> allSelectedParticipantAccounts = extractSelectedParticipantAccounts();

        if (allSelectedParticipantAccounts.size() == 0) {
            errorDisplay.addError("Please select a participant");
            return false;
        }

        return true;

    }

    private List<Account> extractSelectedParticipantAccounts() {
        AccountExtractor ace = new AccountExtractor(userAccount);

        List<String> allSelectedParticipantUserNames = retrieveSelectedParticipantUserNames();

        allSelectedParticipantAccounts = ace.extractAccountsListFromUserNameList(allSelectedParticipantUserNames);

        return allSelectedParticipantAccounts;
    }

    private List<String> retrieveSelectedParticipantUserNames() {
        List<String> listOfSelectedUserNames = new ArrayList<>();

        for (CheckBox c : listOfAllPossibleParticipantCheckBoxes) {
            if (c.isSelected()) {
                listOfSelectedUserNames.add(c.getId());
            }
        }

        return listOfSelectedUserNames;
    }


    private void saveEditedProject(ActionEvent actionEvent) {
         boolean valid = validateAllInputs();

         if (valid) {
             project.updateProjectInfo(projectNameEditable.getCurrentInformation(), projectDescriptionLabelEditable.getCurrentInformation(), allSelectedParticipantAccounts);
             errorDisplay.displaySuccessMessage("Project Update");

             System.out.println(allSelectedParticipantAccounts.stream().map(a -> a.getFirstName()).collect(Collectors.toList()).toString());
         }

    }


    private void closeProject(ActionEvent actionEvent) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle("Delete this project?");
        alert.setHeaderText("Do you wish to delete this Project: " + project.getName());
        alert.setContentText("This will permanently erase the project");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            project.close();
            goBack();
        } else {
            // ... user chose CANCEL or closed the dialog
        }
    }

    /**
     * Opens the page to create a new ticket
     * @param actionEvent
     */
    private void openCreateTicketPage(ActionEvent actionEvent) {
        displayGridPane.getChildren().clear();
        CreateTicketPage createTicketPage = new CreateTicketPage(project, displayGridPane, userAccount);
    }




}
