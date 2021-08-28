package sample;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class SignUpPageGUI {

    private Positions positionList;

    private List<CheckBox> allPositionCheckBoxes;   private List<CheckBox> allAdminPositionCheckBoxes;
    private List<String> allSelectedPositions;     private List<String> allSelectedAdminPositions;

    private Label userNameSL;   private TextField userNameST;
    private Label passwordSL;   private PasswordField passwordST;
    private Label firstNameSL;  private TextField firstNameST;
    private Label lastNameSL;   private TextField lastNameST;
    private Label dobSL;        private  ComboBox<Integer> yearBSC;
                                private  ComboBox<Integer> monthBSC;
                                private ComboBox<Integer> dayBSC;

    private Label titlePositionsSL;
    private Label titleAdminPositionsSL;

    private Button signUpButton;

    private Button backToLoginScreenButton;


    private GridPane suPane; // the the grid Pane that holds all the fields for sign up info

    private Stage stage;
    private Scene signUpScene;

    private LoginOrSignUpGUI login;

    private ErrorDisplay ed;

    private AllCreatedAccounts accountsList;

    private Customiser customiser;


    /**
     * constructor takes on the task of constructing most objects within the
     * class. Also adjust some properties of some objects such as Font for labels
     * and size of GridPanes
     * @param accountsList
     *
     */
    public SignUpPageGUI(AllCreatedAccounts accountsList)
    {

        this.accountsList = AllCreatedAccounts.getInstance();
        customiser = Customiser.getInstance();

        allPositionCheckBoxes = new ArrayList<>();
        allAdminPositionCheckBoxes = new ArrayList<>();

        userNameSL = new Label("Username: ");
        passwordSL = new Label("Password: ");
        firstNameSL = new Label("First Name: ");
        lastNameSL = new Label("Last Name:");
        dobSL = new Label("Date Of Birth");

        userNameST = new TextField();
        userNameST.setPromptText(("Username"));
        passwordST = new PasswordField();
        passwordST.setPromptText("Password");
        firstNameST = new TextField();
        firstNameST.setPromptText("First Name");
        lastNameST = new TextField();
        lastNameST.setPromptText("Last Name");

        signUpButton = new Button("Sign Up");

        backToLoginScreenButton = new Button("Back");
//        backToLoginScreenButton.setMinSize(20,20);

        backToLoginScreenButton.setOnAction(this::backToLoginPage);

        signUpButton.setOnAction(this::completeSignUp);

        customiser.addLabelledNodeToDefaultCustomisation(userNameSL, passwordSL, firstNameSL, lastNameSL, dobSL,
        signUpButton, backToLoginScreenButton);



        fillComboBoxes();


        positionList = new Positions();

        titlePositionsSL = new Label("Positions");
        titlePositionsSL.setFont(new Font("Helvetica", 20));
        titleAdminPositionsSL = new Label("Administration Positions");
        titleAdminPositionsSL.setFont(new Font("Helvetica", 20));

        suPane = new GridPane();

        createPositionsCheckBoxes();
        createAdminPositionsCheckBoxes();

        List<TextField> allTextFields = new ArrayList<>();

        allTextFields.add(userNameST);
        allTextFields.add(passwordST);
        allTextFields.add(firstNameST);
        allTextFields.add(lastNameST);

        for (TextField t : allTextFields) {
            t.setFont(new Font("FiraGo", 14));
        }


        suPane.setPadding(new Insets(10,10,10,10));
        suPane.setMinSize(500,500);
        suPane.setMaxSize(1000,1000);
        suPane.setVgap(10);
        suPane.setHgap(10);

    }

    public void setScene(Scene scene) {
        signUpScene = scene;
    }

    /**
     * Auxillery method to fill out combo boxes
     */
    private void fillComboBoxes() {
        // fill day combo
        ObservableList<Integer> dayBoptions =
                FXCollections.observableArrayList(
                        1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,
                        21,22,23,24,25,26,27,28,29,30,31
                );
        dayBSC = new ComboBox(dayBoptions);
        dayBSC.setPromptText("Day");

        // fill month combo
        ObservableList<Integer> monthBoptions =
                FXCollections.observableArrayList(
                        1,2,3,4,5,6,7,8,9,10,11,12
                );

        monthBSC = new ComboBox(monthBoptions);
        monthBSC.setPromptText("Month");

        // fill the Year combo
        ArrayList<Integer> yearBoptions = new ArrayList<>();

        for (int i = 1900; i <LocalDate.now().getYear(); i++ ) {
            yearBoptions.add(i);
        }

        yearBSC = new ComboBox(FXCollections.observableList(yearBoptions));
        yearBSC.setPromptText("Year");

        int minSizeOfComboBoxes = 25;

       yearBSC.setMinSize(minSizeOfComboBoxes,minSizeOfComboBoxes);
       monthBSC.setMinSize(minSizeOfComboBoxes,minSizeOfComboBoxes);
        dayBSC.setMinSize(minSizeOfComboBoxes,minSizeOfComboBoxes);


    }

    /**
     * Creates and places all position check boxes inside the GridPane
     * All check boxes are dynamic and will change when positions/adminPositions
     * class is updated
     */
    public void createPositionsCheckBoxes() {
        int col = 21;

        for (String p : positionList.getValidPositions()) {

            CheckBox checkBox = new CheckBox(p);

            allPositionCheckBoxes.add(checkBox);

            suPane.add(checkBox,  col, 20);
            col = col + 2;
        }


        for (Labeled labeled : allPositionCheckBoxes) {
            labeled.setFont(new Font("FiraGo", 16));
        }


    }

    /**
     * Creates and places all admin position check boxes inside the GridPane
     * All check boxes are dynamic and will change when positions/adminPositions
     * class is updated
     */
    public void createAdminPositionsCheckBoxes() {
        int aCol = 21;

        for (String ap : positionList.getValidAdministratorPositions()) {
            CheckBox adminCheckBox = new CheckBox(ap);

            allAdminPositionCheckBoxes.add(adminCheckBox);

            suPane.add(adminCheckBox,  aCol, 22);
            aCol = aCol + 2;
        }

        for (Labeled labeled : allAdminPositionCheckBoxes) {
            labeled.setFont(new Font("FiraGo", 16));
        }
    }

    /**
     * Responsible for adding the GUI components to the GUI window
     * and returns the stage
     * @return Stage the Sign up Page Stage
     */
    public Stage createSignUpGUI() {
        ed = new ErrorDisplay();

        suPane.add(backToLoginScreenButton, 28, 4);

        suPane.add(userNameSL, 20, 5);  suPane.add(userNameST, 25, 5);
        suPane.add(passwordSL, 20, 6);  suPane.add(passwordST, 25, 6);
        suPane.add(firstNameSL, 20, 7);  suPane.add(firstNameST, 25, 7);
        suPane.add(lastNameSL, 20, 8);  suPane.add(lastNameST, 25, 8);
        suPane.add(dobSL, 20, 10); suPane.add(dayBSC, 23,10); suPane.add(monthBSC, 24,10); suPane.add(yearBSC, 25,10);

        suPane.add(titlePositionsSL, 20, 20);
        suPane.add(titleAdminPositionsSL,20, 22 );

        suPane.add(signUpButton, 20, 23);   suPane.add(ed.getErrorPane(), 21, 24);

        stage = new Stage();
        //signUpScene = new Scene(suPane, 1200,800);

        stage.setTitle("Sign Up");
        stage.setScene(signUpScene);

        return stage;

    }

    public void backToLoginPage(ActionEvent event) {

        login = new LoginOrSignUpGUI();

        Scene sceneLogin = new Scene(login.getLSPage(), 1430, 730);


        stage.setScene(sceneLogin);

        login.addStage(stage);

    }

    public GridPane getSignUpPagePane() {
        return suPane;
    }

    /**
     * An auxiliary method that retrieves a list of all
     * the text fields within the sign up page
     * @return ArrayList of all the text fields
     */
    private ArrayList<TextField> getAllTextFieldsList() {

        ArrayList<TextField> allTextFields = new ArrayList<>();

        for (Node n : suPane.getChildren()) {
            if (n instanceof TextField) {
                allTextFields.add((TextField) n);
            }
        }

        return allTextFields;
    }

    /**
     * A general method that validates all inputs that the user has entered
     * uses a validator object created within the method
     * and the error display is passed into the validator
     * If an input is not entered then an the error is added to the error display
     */
    private boolean validateALlInputs() {

        Validator val = new Validator(ed);

        if (!val.validateForNonEmptyTextFieldsList(getAllTextFieldsList())) {
            return false;
        }


        if (!(val.validateForAnyCheckBoxSelection(allPositionCheckBoxes) || val.validateForAnyCheckBoxSelection(allAdminPositionCheckBoxes))) {
            ed.addError("Please enter an entry for any position");
            return false;
        }

        if (!val.validateForNullDateOfBirth(dayBSC, monthBSC, yearBSC)) {
            return false;
        }

         return  true;
    }

    /**
     * Completes the sign up process, called when the sign up button
     * is pressed. If there is an error
     * the process is halted, otherwise it continues and a new
     * account is added to the accounts list within the  AllCreatedAccounts class.
     * @param event
     */
    private void completeSignUp(ActionEvent event) {
        //clears error display as another validation is about to take place
        ed.clearErrorPane();

        allSelectedAdminPositions = new ArrayList<>();
        allSelectedPositions = new ArrayList<>();

        for (CheckBox c : allPositionCheckBoxes) {
            if (c.isSelected()) {
                allSelectedPositions.add(c.getText());
            }
        }

        for (CheckBox ca : allAdminPositionCheckBoxes) {
            if (ca.isSelected()) {
                allSelectedAdminPositions.add(ca.getText());
            }
        }

        boolean valid = validateALlInputs();
        try {

            if (valid) {

                accountsList.addAccount(userNameST.getText(), passwordST.getText(), firstNameST.getText(),
                       lastNameST.getText(), yearBSC.getValue(), monthBSC.getValue(), dayBSC.getValue(), allSelectedPositions.toArray(new String[allSelectedPositions.size()]), allSelectedAdminPositions.toArray(new String[allSelectedAdminPositions.size()]));

            }

        } catch(AdminPositionNotFoundException e) {
            System.out.println("Admin positions invalid");
        } catch(PositionNotFoundException e) {
            System.out.println("Position not found");
        } catch (DuplicateUsernameException e) {
              ed.addError(e.getMessage());
        } catch (DuplicatePasswordException e) {
             ed.addError(e.getMessage());
        }

    }


    public void addStage(Stage enteredStage) {

        stage = enteredStage;
    }
}
