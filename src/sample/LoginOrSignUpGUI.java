package sample;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;

public class LoginOrSignUpGUI {

    private GridPane lsPane;

    private Label userNameLabel;
    private TextField userName;

    private Label passWordLabel;
    private TextField passWord;

    private Button loginButton;
    private Button signUpButton;

    private AllCreatedAccounts accountsList;

    private ErrorDisplay ed;

    private SignUpPageGUI signUpPage;


    private Stage mainStage;

    private Customiser customiser;


    /**
     * Constructor responsible for adjusting the GridPane
     * and calling the createLSPage() method
     *
     */
    public LoginOrSignUpGUI() {

       // this.accountsList  = accountsList;

        accountsList = AllCreatedAccounts.getInstance();
        customiser = Customiser.getInstance();

        ed = new ErrorDisplay();

        lsPane = new GridPane();
        lsPane.setPadding(new Insets(10,10,10,10));
        lsPane.setMinSize(700,700);
        lsPane.setMaxSize(700,700);
        lsPane.setVgap(10);
        lsPane.setHgap(10);

        createLSPage();
    }

    /**
     * Creates the basic GUI of the LoginSignUpPage
     */
    public void createLSPage() {

        Label lsTitle = new Label("TraxErr");
        lsTitle.setFont(new Font("Arial Black",35));

        lsPane.add(lsTitle,35,20);

        userNameLabel = new Label("Username: ");
       // userNameLabel.setFont(new Font("Calibri Light", 24));

        lsPane.add(userNameLabel,35,25);

        userName = new TextField("qwe1");
        userName.setMinSize(30,30);
        userName.setPromptText("Username");
        lsPane.add(userName, 38,25);

        passWordLabel = new Label("Password: ");
     //   passWordLabel.setFont(new Font("Calibri Light",24));
        lsPane.add(passWordLabel, 35, 30);

        passWord = new PasswordField();
        passWord.setText("qwe");
        passWord.setMinSize(30,30);
        passWord.setPromptText("Password");
        lsPane.add(passWord, 38,30);

        loginButton = new Button("Login");
        loginButton.setMinSize(30,30);
        //loginButton.setFont(new Font("Calibri light", 24));
        lsPane.add(loginButton, 35, 32 );

        signUpButton = new Button("Sign Up");
        signUpButton.setMinSize(30,30);
      ///  signUpButton.setFont(new Font("Calibri light", 24));
        lsPane.add(signUpButton, 38, 32 );

        customiser.customiseListOfNodesToFont("FiraGo", 24, signUpButton, loginButton);
        customiser.customiseListOfNodesToFont("FiraGo", 24, userNameLabel, passWordLabel);

        loginButton.setOnAction(this::completeLogin);
        signUpButton.setOnAction(this::openSignUpPage);

        lsPane.add(ed.getErrorPane(),35,33);

    }

    /**
     * Retrieves the GridPane of the Login/Sign up Page
     * @return GridPane the GridPane of the LS page
     */
    public GridPane getLSPage() {
        return lsPane;
    }

    public void showAccountsList(ActionEvent event) {
        accountsList.showAllAccountsText();
    }


    /**
     * Opens the sign up page in a new window, called upon by sign up Button
     * @param event
     */
    private void openSignUpPage(ActionEvent event)  {

        System.out.println("Before scene in stage:  " + mainStage.getScene().toString());

        signUpPage = new SignUpPageGUI(accountsList);
        signUpPage.createSignUpGUI();

        Scene sc = new Scene(signUpPage.getSignUpPagePane(), 1430, 730);

        mainStage.setScene(sc);


        signUpPage.addStage(mainStage);

    }

    /**
     * uses the validator object to validate the textfields of the login page for any
     * empty fields
     * @return
     */
    private boolean validateAllInputs() {
        Validator val = new Validator(ed);

        ArrayList<TextField> allTextFieldsOfLoginPage = new ArrayList<>();

        allTextFieldsOfLoginPage.add(userName);
        allTextFieldsOfLoginPage.add(passWord);

        ed.displaySuccessMessage("hi");

        return val.validateForNonEmptyTextFieldsList(allTextFieldsOfLoginPage);

    }

    /**
     * This method completes the login process by validating all input fields
     * And matching delegating the matching process to the accountsList object
     * which finds the account of returns null if not found.
     * @param event
     */
    private void completeLogin(ActionEvent event) {
        ed.clearErrorPane();

        Account foundAccount = null;

        if (validateAllInputs()) {

            try {
               foundAccount =  accountsList.findAccountUsingLogInInfo(userName.getText(), passWord.getText());

            } catch (AccountNotFoundException e) {
                    ed.addError(e.getMessage());
            }

        }

        if(foundAccount != null) {


            System.out.println(foundAccount.getFirstName() + "  " + foundAccount.getAge());
            System.out.println("passed");
            System.out.println();


            AccountPage accPage = new AccountPage(foundAccount, accountsList);
            Scene accountScene = new Scene(accPage.getEntryMainPane(), 1430,730);
            mainStage.setScene(accountScene);
            accPage.addStage(mainStage);

        } else {
            System.out.println("Account not found");
        }


    }



    /**
     * Setter method that adds a stage object to the
     * LS object
     * @param enteredPrimaryStage
     */
    public void addStage(Stage enteredPrimaryStage) {

        mainStage = enteredPrimaryStage;
    }

}
