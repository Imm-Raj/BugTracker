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

public class CreateTicketPage {

    private Project project;
    private GridPane displayGridPane;
    private Account userAccount;

    private Button backToProjectInfoDisplayButton;

    private Customiser customiser;

    private BorderPane mainPane;

    private HBox centralHBox;

    private VBox rightVbox;

    private List<CheckBox> listOfAllAssignedEmpBoxes;

    private List<TextField> listOfAllInputFields;

    private  Label createTicketPageTitle;

    private Label ticketNameLab;
    private Label ticketDescriptionNameLab;

    private TextField ticketNameTextField;
    private TextField ticketDescriptionTextField;

    private Label priorityLabel;
    private Label statusLabel;

    private Slider prioritySlider;

    private Slider statusSlider;

    private  Label ticketParticipantsLabel;

    private Button createTicketButton;

    private GridPane centerGridPane;

    private ErrorDisplay errorDisplay;

    private Validator validator;

    private AllCreatedProjects allCreatedProjects;

    private AllCreatedAccounts allCreatedAccounts;

    private Priorities priorities;

    private Statuses statuses;

    public CreateTicketPage(Project project, GridPane displayGridPane, Account userAccount) {

        this.project = project;
        this.displayGridPane = displayGridPane;
        this.userAccount = userAccount;


        allCreatedAccounts = AllCreatedAccounts.getInstance();
        allCreatedProjects = AllCreatedProjects.getInstance();

        customiser = Customiser.getInstance();

        priorities = new Priorities();
        statuses = new Statuses();

        mainPane = new BorderPane();

        listOfAllAssignedEmpBoxes = new ArrayList<>();
        listOfAllInputFields = new ArrayList<>();

        rightVbox = new VBox();
        rightVbox.setSpacing(200);

        centerGridPane = new GridPane();
        centerGridPane.setPadding(new Insets(0,100,0,0));
        centerGridPane.setMinHeight(450);
        //  centerGridPane.setVgap(5);
        //  centerGridPane.setHgap(5);

        backToProjectInfoDisplayButton = new Button("Back");
        backToProjectInfoDisplayButton.setPadding(new Insets(10,10,10,10));
        backToProjectInfoDisplayButton.setOnAction(this::backToProjectInfoDisplayPage);

        int inputNodesPadding = 1;
        int titleLabelPadding = 30;

        createTicketPageTitle = new Label("Create Ticket");

        createTicketPageTitle.setPadding(new Insets(titleLabelPadding,titleLabelPadding,titleLabelPadding,0));

        ticketNameLab = new Label("Name: ");
        ticketNameLab.setPadding(new Insets(inputNodesPadding,inputNodesPadding,inputNodesPadding,inputNodesPadding));

        ticketNameTextField = new TextField();
        ticketNameTextField.setPromptText("Ticket Name");
        ticketNameTextField.setPadding(new Insets(inputNodesPadding,inputNodesPadding,inputNodesPadding,inputNodesPadding));
        listOfAllInputFields.add(ticketNameTextField);


        ticketDescriptionNameLab = new Label("Description:  ");
        ticketDescriptionNameLab.setPadding(new Insets(inputNodesPadding,inputNodesPadding,inputNodesPadding,inputNodesPadding));

        ticketDescriptionTextField = new TextField();
        ticketDescriptionTextField.setPromptText("Ticket Description");
        ticketDescriptionTextField.setPadding(new Insets(inputNodesPadding,inputNodesPadding,inputNodesPadding,inputNodesPadding));
        ticketDescriptionTextField.setPrefSize(300,300);
        listOfAllInputFields.add(ticketDescriptionTextField);

         priorityLabel = new Label("Priority: ");
         statusLabel = new Label("Status: ");


        ticketParticipantsLabel = new Label("Assigned To: ");


        createTicketButton = new Button("Create");
        int createTicketButtonInsets = 10;
        createTicketButton.setPadding(new Insets(createTicketButtonInsets,createTicketButtonInsets,createTicketButtonInsets,createTicketButtonInsets));
        createTicketButton.setOnAction(this::completeTicketCreation);

        customiser.addLabelledNodeToDefaultCustomisation(backToProjectInfoDisplayButton, ticketNameLab, ticketDescriptionNameLab, ticketParticipantsLabel,
                createTicketButton, priorityLabel, statusLabel);

        customiser.addTextInputControlNodeToDefaultCustomisation(ticketNameTextField, ticketDescriptionTextField);

        customiser.customiseListOfNodesToFont("FiraGo", 35, createTicketPageTitle);

        errorDisplay = new ErrorDisplay();

        validator = new Validator(errorDisplay);

        build();


    }

    public Project getProject() {
        return project;
    }

    public Account getUserAccount() {
        return userAccount;
    }

    public BorderPane getMainPane() {
        return mainPane;
    }

    private void build() {

        centralHBox = new HBox();

        displayGridPane.getChildren().add(mainPane);

        mainPane.setCenter(centralHBox);

        centralHBox.getChildren().add(centerGridPane);

        mainPane.setTop(createTicketPageTitle);

        mainPane.setRight(rightVbox);

        rightVbox.getChildren().add(backToProjectInfoDisplayButton);

        rightVbox.getChildren().add(createTicketButton);



        buildCenterPane();

        buildSliders();

    }

    private void buildSliders() {
        

        //Build Priority Slider

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


        //Finalises slider creation and puts it inside the VBox to display
        int sliderPadding = 2;

        int gap = 15;

        VBox sliderVbox = new VBox();


        sliderVbox.setPadding(new Insets(sliderPadding, sliderPadding, sliderPadding, sliderPadding));

        sliderVbox.getChildren().addAll(priorityLabel, prioritySlider);

        statusLabel.setPadding(new Insets(gap,0,0,0));

        sliderVbox.getChildren().addAll(statusLabel, statusSlider);

        centralHBox.getChildren().add(sliderVbox);

        //Test code to see if the values are being accurately produced
        prioritySlider.setOnMouseEntered(e -> System.out.println("Value Of priority: " + prioritySlider.getValue()));
        statusSlider.setOnMouseEntered(e -> System.out.println("Value Of status: " + statusSlider.getValue()));


    }

    private void buildCenterPane() {

        centerGridPane.add(ticketNameLab,0,0);
        centerGridPane.add(ticketNameTextField,10,0);

        centerGridPane.add(ticketDescriptionNameLab, 0,1);
        centerGridPane.add(ticketDescriptionTextField, 10,1);

        centerGridPane.add(ticketParticipantsLabel, 0, 2 );

        buildCheckBoxesForParticipantSelection();

        centerGridPane.add(errorDisplay.getErrorPane(), 10,3);


//        mainPane.setCenter(centerGridPane);

    }

    /**
     * Builds the checkbox for all the employees that are partaking in the project
     * Sets the userName of the account to the checkbox identifying the
     * Account so that each checkBox can be uniquely identified
     */
    private void buildCheckBoxesForParticipantSelection() {

        int ver = 3; int hor = 1;

        int horLimit = 1;

        for (Account account : project.getParticipants()) {

            CheckBox empCheckBox = new CheckBox("" + account.getFirstName() + " " + account.getLastName());
            empCheckBox.setId(account.getUserName());

            empCheckBox.setPadding(new Insets(2,2,2,2));
            centerGridPane.add(empCheckBox, hor, ver);
            customiser.addCheckBoxNodeToDefaultCustomisation(empCheckBox);

            listOfAllAssignedEmpBoxes.add(empCheckBox);


            if (hor == horLimit) {
                hor=0;
                ver++;
            }
            hor++;

        }
    }

    /**
     * Validates all input nodes to make sure that an appropriate input
     * has been made for all of them
     * @return true if input is correct, false otherwise
     */
    private boolean validateAllInputs() {

        errorDisplay.clearErrorPane();

        if (!validator.validateForNonEmptyTextFieldsList(listOfAllInputFields)) {
            return false;
        }

        if (!validator.validateForAnyCheckBoxSelection(listOfAllAssignedEmpBoxes)) {
            errorDisplay.addError("Please enter a assigned to employee");
            return false;
        }

        if (validator.validateIsAboveCharacterLimit(30, ticketNameTextField.getText(), "Ticket Name")) {
            return false;
        }

        if (validator.validateIsAboveCharacterLimit(400, ticketDescriptionTextField.getText(), "Ticket Description")) {
            return false;
        }


        return true;
    }

    private void backToProjectInfoDisplayPage(ActionEvent actionEvent) {
        displayGridPane.getChildren().clear();

        ProjectEditableInfoDisplay projectEditableInfoDisplay = new ProjectEditableInfoDisplay(displayGridPane, new ProjectViewState(userAccount), project, "View Project", userAccount);
    }

    /**
     * Validates for any errors or mistakes and completes the creation of the ticket if passed
     * @param actionEvent
     */
    private void completeTicketCreation(ActionEvent actionEvent) {

        boolean valid = validateAllInputs();

        if (valid) {
            List<String> listOfSelectedEmpUserNames = new ArrayList<>();

            for (CheckBox ch : listOfAllAssignedEmpBoxes) {
                if (ch.isSelected()) {
                    listOfSelectedEmpUserNames.add(ch.getId());
                    System.out.println(ch.getId());
                }
            }

            String ticketName = ticketNameTextField.getText();
            String ticketDescription = ticketDescriptionTextField.getText();

            TicketCreator ticketCreator = new TicketCreator(project);

            try {
                ticketCreator.createTicketForProject(ticketNameTextField.getText(), ticketDescriptionTextField.getText(), prioritySlider.getValue(), statusSlider.getValue(), userAccount, listOfSelectedEmpUserNames);
                errorDisplay.displaySuccessMessage("Ticket Creation ");
            } catch (AccountNotFoundException e) {
                e.printStackTrace();
            } catch (AllReadyExistsException e) {
                errorDisplay.addError("Ticket of Name: " + ticketNameTextField.getText() +  " already exists");
            } catch (EmptyAccountsListException e) {
                e.printStackTrace();
            } catch (CreatorAccountNullException e) {
                e.printStackTrace();
            }

        }

    }

}
