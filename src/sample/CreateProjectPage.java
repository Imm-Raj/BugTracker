package sample;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class CreateProjectPage {


    private BorderPane mainProjectPane;

    private GridPane insideGridPane;
    private Account account;

    private Button backToProjectViewButton;

    private Customiser customiser;

    private VBox rightVbox;
    private VBox centerVBox;

    private List<CheckBox> listOfAllEmpBoxes;

    private List<TextField> listOfAllInputFields;


    private AllCreatedProjects allCreatedProjects;

    private AllCreatedAccounts allCreatedAccounts;

    private  Label createProjectPageTitle;

    private Label projectNameLab;
    private Label projectDescriptionNameLab;

    private TextField projectNameTextField;
    private TextField projectDescriptionTextField;

    private  Label projectParticipantsLabel;

    private Button createProjectButton;

    private GridPane centerGridPane;

    private ErrorDisplay errorDisplay;

    private Validator validator;


    public CreateProjectPage(GridPane insideGrid, Account account) {

        insideGridPane = insideGrid;
        this.account = account;

        allCreatedAccounts = AllCreatedAccounts.getInstance();
        allCreatedProjects = AllCreatedProjects.getInstance();

        customiser = Customiser.getInstance();

        mainProjectPane = new BorderPane();


        listOfAllEmpBoxes = new ArrayList<>();
        listOfAllInputFields = new ArrayList<>();


        rightVbox = new VBox();
        rightVbox.setSpacing(200);

        centerGridPane = new GridPane();
        centerGridPane.setPadding(new Insets(0,100,0,0));
        centerGridPane.setMinHeight(450);
      //  centerGridPane.setVgap(5);
      //  centerGridPane.setHgap(5);

        backToProjectViewButton = new Button("Back");
        backToProjectViewButton.setPadding(new Insets(10,10,10,10));
        backToProjectViewButton.setOnAction(this::backToProjectView);
        
        int inputNodesPadding = 1;
        int titleLabelPadding = 30;

        createProjectPageTitle = new Label("Create Project");

        createProjectPageTitle.setPadding(new Insets(titleLabelPadding,titleLabelPadding,titleLabelPadding,0));

        projectNameLab = new Label("Name: ");
        projectNameLab.setPadding(new Insets(inputNodesPadding,inputNodesPadding,inputNodesPadding,inputNodesPadding));

        projectNameTextField = new TextField();
        projectNameTextField.setPromptText("Project Name");
        projectNameTextField.setPadding(new Insets(inputNodesPadding,inputNodesPadding,inputNodesPadding,inputNodesPadding));
        listOfAllInputFields.add(projectNameTextField);


        projectDescriptionNameLab = new Label("Description:  ");
        projectDescriptionNameLab.setPadding(new Insets(inputNodesPadding,inputNodesPadding,inputNodesPadding,inputNodesPadding));

        projectDescriptionTextField = new TextField();
        projectDescriptionTextField.setPromptText("Project Description");
        projectDescriptionTextField.setPadding(new Insets(inputNodesPadding,inputNodesPadding,inputNodesPadding,inputNodesPadding));
        projectDescriptionTextField.setMinSize(300,200);
        projectDescriptionTextField.setMaxSize(300,200);
        listOfAllInputFields.add(projectDescriptionTextField);


        projectParticipantsLabel = new Label("Participants: ");


        createProjectButton = new Button("Create Project");
        int createProjectButtonInsets = 10;
        createProjectButton.setPadding(new Insets(createProjectButtonInsets,createProjectButtonInsets,createProjectButtonInsets,createProjectButtonInsets));
        createProjectButton.setOnAction(this::completeProjectCreation);

        customiser.addLabelledNodeToDefaultCustomisation(backToProjectViewButton, projectNameLab, projectDescriptionNameLab, projectParticipantsLabel,
                createProjectButton);

        customiser.addTextInputControlNodeToDefaultCustomisation(projectNameTextField, projectDescriptionTextField);

        customiser.customiseListOfNodesToFont("FiraGo", 35, createProjectPageTitle);



        errorDisplay = new ErrorDisplay();

        validator = new Validator(errorDisplay);

        build();

    }

    public BorderPane getMainProjectPane() {
        return mainProjectPane;
    }

    public Account getAccount() {
        return account;
    }

    private void build() {

        mainProjectPane.setTop(createProjectPageTitle);

        mainProjectPane.setRight(rightVbox);

        rightVbox.getChildren().add(backToProjectViewButton);

        rightVbox.getChildren().add(createProjectButton);



        buildCenterPane();

    }

    private void buildCenterPane() {

        centerGridPane.add(projectNameLab,0,0);
        centerGridPane.add(projectNameTextField,10,0);

        centerGridPane.add(projectDescriptionNameLab, 0,1);
        centerGridPane.add(projectDescriptionTextField, 10,1);

        centerGridPane.add(projectParticipantsLabel, 0, 2 );

        buildCheckBoxesForParticipantSelection();

        centerGridPane.add(errorDisplay.getErrorPane(), 10,3);


        mainProjectPane.setCenter(centerGridPane);

    }

    /**
     * Builds the checkbox for all the employees within the team
     * Sets the userName of the account to the checkbox identifying the
     * Account so that each checkBox can be uniquely identified
     */
    private void buildCheckBoxesForParticipantSelection() {

        int ver = 3; int hor = 1;

        for (Account account : allCreatedAccounts.getAccountsList()) {

            CheckBox empCheckBox = new CheckBox("" + account.getFirstName() + " " + account.getLastName());
            empCheckBox.setId(account.getUserName());

            empCheckBox.setPadding(new Insets(2,2,2,2));
            centerGridPane.add(empCheckBox, hor, ver);
            customiser.addCheckBoxNodeToDefaultCustomisation(empCheckBox);

            listOfAllEmpBoxes.add(empCheckBox);

            if (hor == 3) {
                hor=0;
                ver++;
            }
            hor++;

        }
    }

    private void backToProjectView(ActionEvent actionEvent) {

        insideGridPane.getChildren().clear();

        ProjectViewState projectView = new ProjectViewState(account);

        projectView.setGridPane(insideGridPane); //Current grid pane is passed in

        projectView.build(); //The project view page builds itself on the grid pane that has been passed in



       // insideGridPane.add(projectView.getMainPane(), 0,0);

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

        if (!validator.validateForAnyCheckBoxSelection(listOfAllEmpBoxes)) {
            errorDisplay.addError("Please enter a participating employee");
            return false;
        }

        if (validator.validateIsAboveCharacterLimit(20, projectNameTextField.getText(), "Project Name")) {
            return false;
        }

        if (validator.validateIsAboveCharacterLimit(300, projectDescriptionTextField.getText(), "Project Description")) {
            return false;
        }


        return true;
    }

    /**
     * Completes project creation validating input and gathering all data entered by user
     * Then utilising projectCreator class
     */
    private void completeProjectCreation(ActionEvent event) {

        boolean valid = validateAllInputs();

        if (valid) {
            List<String> listOfSelectedEmpUserNames = new ArrayList<>();

            for (CheckBox ch : listOfAllEmpBoxes) {
                if (ch.isSelected()) {
                    listOfSelectedEmpUserNames.add(ch.getId());
                    System.out.println(ch.getId());
                }
            }

            String projectName = projectNameTextField.getText();
            String projectDescription = projectDescriptionTextField.getText();

            ProjectCreator projectCreator = new ProjectCreator(account, errorDisplay);

            try {
                projectCreator.createProject(listOfSelectedEmpUserNames, account, projectName, projectDescription);
                errorDisplay.displaySuccessMessage("Project Creation");
            } catch (AllReadyExistsException e) {
                errorDisplay.addError("The project with name: " + projectName + " already exists");
            } catch (EmptyAccountsListException e) {
                errorDisplay.addError("Participants entered are empty");
            } catch (CreatorAccountNullException e) {
                errorDisplay.addError("Creator Account is null");
            }


        }

    }




}
