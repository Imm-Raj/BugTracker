package sample;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TicketEditableInfoDisplay extends EditableInfoDisplay {


    private Ticket ticket;

    private Project associatedProject;

    private boolean isViewOnly;

    private Label ticketNameLabel;
    private EditableLabel ticketNameEditable;
    private Label ticketDescriptionLabel;
    private EditableLabel ticketDescriptionLabelEditable;
    private Label ticketCreatedByLabel;
    private Label ticketCreatedByLabelInfo;
    private Label ticketDateCreatedLabel;
    private Label ticketDateCreatedLabelInfo;
    private Label ticketIDLabel;
    private Label ticketIDInfo;
    private Label ticketAssociatedProjectLabel;
    private Label ticketAssociatedProjectInfo;

    private HBox centerHBox;

    private BorderPane rightBorderPane;

    private VBox rightTopVbox;

    private VBox rightBottomVBox;
    
    private VBox sliderVBox;


    private Button saveButton;

    private Button closeButton;

    private Button completeButton;

    private ErrorDisplay errorDisplay;

    private Validator validator;

    private List<CheckBox> listOfAllPossibleParticipantCheckBoxes;
    private List<Account> allSelectedParticipantAccounts;
    
    private Slider prioritySlider;
    private Slider statusSlider;
    
    private Priorities priorities;
    private Statuses statuses;

    private Label priorityLabel;
    private Label statusLabel;

    private Button backViewButton;

    private Customiser customiser;

    public TicketEditableInfoDisplay(GridPane gridPaneToDisplayOn, Ticket ticketToDisplay, Project project, String title, Account userAccount, boolean isViewOnly) {
        super(gridPaneToDisplayOn, title, userAccount);

        ticket = ticketToDisplay;
        associatedProject = project;
        this.isViewOnly = isViewOnly;

        errorDisplay = new ErrorDisplay();

        customiser = Customiser.getInstance();

        validator = new Validator(errorDisplay);

        isAccountAnEditor = userAccount instanceof AdministratorAccount || userAccount == ticket.getCreatedBy();

        System.out.println(userAccount.getFirstName()  + " " + isAccountAnEditor);

        if (project == null) {
            throw new NullPointerException("Project is NUll cannot display in editable display");
        } else {
            ticketNameLabel = new Label("Name: ");                ticketNameEditable = new EditableLabel(ticket.getName());
            ticketDescriptionLabel = new Label("Description: ");  ticketDescriptionLabelEditable = new EditableLabel(ticket.getDescription());
            ticketCreatedByLabel = new Label("Created By: ");     ticketCreatedByLabelInfo = new Label(ticket.getCreatedBy().getFirstName() + " " +  ticket.getCreatedBy().getLastName());
            ticketDateCreatedLabel = new Label("Date Created: "); ticketDateCreatedLabelInfo = new Label(ticket.getDateCreated().toString());
            ticketIDLabel = new Label("Ticket ID: ");            ticketIDInfo = new Label(ticket.getTicketId());
            ticketAssociatedProjectLabel = new Label("Associated Project: ");   ticketAssociatedProjectInfo = new Label(ticket.getAssociatedProject().getName());

            saveButton = new Button("  Save  ");
            //saveButton.setPadding(new Insets(0, 0,0,5));
            closeButton = new Button(" Delete ");

            completeButton = new Button("Complete");
            completeButton.setAccessibleRoleDescription("Completes the ticket and removes it");
            completeButton.setOnAction(this::completeTicket);


            if (isViewOnly) {
                backButton.setText("View \nProject");
                backButton.setOnAction(e -> goBackToProjectEditableInfoDisplay());
                backViewButton = new Button("Back");
                backViewButton.setOnAction(e -> goBackToTicketListViewState());
                customiser.addLabelledNodeToDefaultCustomisation(backViewButton);

            } else {
                backButton.setOnAction(e -> goBackToProjectEditableInfoDisplay());
            }


            customiser.addLabelledNodeToDefaultCustomisation(saveButton, closeButton, completeButton);


            processLabeledInfo();

            centerHBox = new HBox();

            rightBorderPane = new BorderPane();

            rightTopVbox = new VBox();
            rightBottomVBox = new VBox();


            buildEditableInfoDisplay();

            buildTicketSpecificEditableInfoDisplay();

        }

    }


    protected void goBackToProjectEditableInfoDisplay() {

        displayGridPane.getChildren().clear();

        ProjectEditableInfoDisplay projectEditableInfoDisplay = new ProjectEditableInfoDisplay(displayGridPane, new ProjectViewState(userAccount),associatedProject, "View Project", userAccount);
    }

    protected void goBackToTicketListViewState() {
        displayGridPane.getChildren().clear();

        TicketsViewState ticketsViewState = new TicketsViewState(userAccount, displayGridPane);
        ticketsViewState.build();
    }

    private void processLabeledInfo() {

        addTitleLabels(ticketNameLabel, ticketDescriptionLabel, ticketCreatedByLabel, ticketDateCreatedLabel, ticketAssociatedProjectLabel, ticketIDLabel);

        if (isAccountAnEditor) {

            addEditableLabels(ticketNameEditable, ticketDescriptionLabelEditable);
            addInfoLabels(ticketCreatedByLabelInfo, ticketDateCreatedLabelInfo, ticketAssociatedProjectInfo, ticketIDInfo);
        } else {

            addInfoLabels(ticketNameEditable.getInfoLabel(), ticketDescriptionLabelEditable.getInfoLabel(), ticketCreatedByLabelInfo, ticketDateCreatedLabelInfo, ticketAssociatedProjectInfo, ticketIDInfo);
        }

    }

    private void buildTicketSpecificEditableInfoDisplay() {

        editableBorderPane.setCenter(centerHBox);

        centerHBox.setMinWidth(700);

        editableBorderPane.setRight(rightBorderPane);

        rightBorderPane.setTop(rightTopVbox);
        rightBorderPane.setBottom(rightBottomVBox);



        rightTopVbox.setSpacing(10);
        rightBottomVBox.setSpacing(10);

        rightBottomVBox.getChildren().addAll(closeButton, completeButton, saveButton);

        if (isViewOnly) {
            rightTopVbox.getChildren().add(backViewButton);
            rightBottomVBox.getChildren().add(backButton);
        } else {
            rightTopVbox.getChildren().addAll(backButton);
        }

        if (!isAccountAnEditor) {
            closeButton.setVisible(false);
            saveButton.setVisible(false);
        }

        saveButton.setOnAction(this::saveEditedTicket);
        closeButton.setOnAction(this::closeTicket);


        centerHBox.getChildren().addAll(centerGrid);

        listOfAllPossibleParticipantCheckBoxes = new ArrayList<>();

        centerGrid.add(errorDisplay.getErrorPane(),10,25);


        fillParticipantCheckBoxes();
        buildPriorityAndStatusSliders();
    }

    private void buildPriorityAndStatusSliders() {
        sliderVBox = new VBox();
        priorities = new Priorities();
        statuses = new Statuses();

        priorityLabel = new Label("Priority: ");
        statusLabel = new Label("Status: ");
        customiser.addLabelledNodeToDefaultCustomisation(priorityLabel, statusLabel);

        prioritySlider = new Slider();
        prioritySlider.setMin(priorities.getMinValueInteger());
        prioritySlider.setMax(priorities.getMaxValueInteger());
        prioritySlider.setValue(1);
        prioritySlider.setMinorTickCount(0);
        prioritySlider.setMajorTickUnit(1);
        prioritySlider.setSnapToTicks(true);
        prioritySlider.setShowTickMarks(true);
        prioritySlider.setShowTickLabels(true);

        prioritySlider.setLabelFormatter(new StringConverter<Double>() {
            @Override
            public String toString(Double n) {

                Integer inIntForm = n.intValue();

                return priorities.getPriorityInWordForm(inIntForm);
            }

            @Override
            public Double fromString(String s) {
                double inDoubleForm = priorities.getPriorityInIntegerForm(s);

                return inDoubleForm;

            }
        });

        //Build Status Slider

        statusSlider = new Slider();
        statusSlider.setMin(priorities.getMinValueInteger());
        statusSlider.setMax(priorities.getMaxValueInteger());
        statusSlider.setValue(1);
        statusSlider.setMinorTickCount(0);
        statusSlider.setMajorTickUnit(1);
        statusSlider.setSnapToTicks(true);
        statusSlider.setShowTickMarks(true);
        statusSlider.setShowTickLabels(true);

        statusSlider.setLabelFormatter(new StringConverter<Double>() {
            @Override
            public String toString(Double n) {

                Integer inIntForm = n.intValue();

                return statuses.getStatusInWordForm(inIntForm);
            }

            @Override
            public Double fromString(String s) {
                double inDoubleForm = statuses.getStatusInIntegerForm(s);

                return inDoubleForm;

            }
        });

        prioritySlider.setMinWidth(380);


        //Sets the sliders to show the priority and status information the ticket has now
        prioritySlider.setValue(ticket.getPriority());
        statusSlider.setValue(ticket.getStatus());

        //Finalises slider creation and puts it inside the VBox to display
        int sliderPadding = 2;

        int gap = 15;

        sliderVBox.setPadding(new Insets(sliderPadding, sliderPadding, sliderPadding, sliderPadding));

        sliderVBox.getChildren().addAll(priorityLabel, prioritySlider);

        statusLabel.setPadding(new Insets(gap,0,0,0));

        sliderVBox.getChildren().addAll(statusLabel, statusSlider);

        centerHBox.getChildren().add(sliderVBox);

        //Test code to see if the values are being accurately produced
        prioritySlider.setOnMouseEntered(e -> System.out.println("Value Of priority: " + prioritySlider.getValue()));
        statusSlider.setOnMouseEntered(e -> System.out.println("Value Of status: " + statusSlider.getValue()));


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

        List<Account> allProjectParticipants = associatedProject.getParticipants();

        for (Account acc : allProjectParticipants) {

            CheckBox ch = new CheckBox(acc.getFirstName() + " " + acc.getLastName());

            ch.setPadding(new Insets(0,10,0,10));

            ch.setId(acc.getUserName());

            customiser.addCheckBoxNodeToDefaultCustomisation(ch);

            if (ticket.checkIfNameIsParticipantOfTicket(acc.getUserName())) {
                ch.setSelected(true);
            }
            listOfAllPossibleParticipantCheckBoxes.add(ch);

        }

        buildParticipantCheckBoxes();

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

    private boolean validateAllInputs() {
        errorDisplay.clearErrorPane();

        if (ticketNameEditable.getCurrentInformation() == "") {
            errorDisplay.addError("Please Enter project name");
            return false;
        }

        if (ticketDescriptionLabelEditable.getCurrentInformation() == "") {
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


    /**
     * Completes the saving procedure and updates the ticket
     * @param actionEvent
     */
    private void saveEditedTicket(ActionEvent actionEvent) {
        boolean valid = validateAllInputs();

        if (valid) {
            ticket.updateTicketInfo(ticketNameEditable.getCurrentInformation(), ticketDescriptionLabelEditable.getCurrentInformation(), allSelectedParticipantAccounts, prioritySlider.getValue(), statusSlider.getValue());
            errorDisplay.displaySuccessMessage("Ticket Update");

            System.out.println(allSelectedParticipantAccounts.stream().map(a -> a.getFirstName()).collect(Collectors.toList()).toString());
        }

    }


    private void closeTicket(ActionEvent actionEvent) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle("Delete this ticket?");
        alert.setHeaderText("Do you wish to close this Ticket: " + ticket.getName());
        alert.setContentText("This will permanently delete the ticket");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            ticket.close();
            System.out.println("IS the ticket: " + ticket.getName() + " complete:" + ticket.isComplete());
            goBackToProjectEditableInfoDisplay();
        } else {
            // ... user chose CANCEL or closed the dialog
        }
    }

    private void completeTicket(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle("Complete this ticket?");
        alert.setHeaderText("Do you wish to complete this Ticket: " + ticket.getName());
        alert.setContentText("This will complete this ticket \n and the ticket cannot" +
                " be accessed anymore");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            ticket.completeTicket(userAccount.getUserName());
            System.out.println("IS the ticket: " + ticket.getName() + " complete:" + ticket.isComplete());
            goBackToProjectEditableInfoDisplay();
        } else {
            // ..
        }

    }



}
