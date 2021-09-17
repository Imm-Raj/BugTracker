package sample;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.HashMap;

public class AccountPage {

    private Account userAccount;
    private AllCreatedAccounts accountsList;
    private Stage stage;

    private BorderPane mainPane;

    private HBox topHBox;
    private HBox accountHBox;

    private Label accountLabel;

    private Button settingsButton;
    private Button accountSettingsButton;
    private Button signOutButton;


    private VBox sideVBox;

    private Button dashBoardButton;
    private Button viewProjectsButton;
    private Button viewTicketsButton;
    private Button viewTeamButton;

    private String fontToChoose;

    private CentralViewState currentCentralView;

    private HashMap<Button, CentralViewState> buttonToViewState;

    private ErrorDisplay ed;


    public AccountPage(Account account, AllCreatedAccounts accountsList) {

        fontToChoose = "FiraGo";

        this.accountsList = accountsList;

        userAccount = account;

        mainPane = new BorderPane();

        topHBox = new HBox();
        topHBox.setMinHeight(100);
        accountHBox = new HBox();

        sideVBox = new VBox();
        sideVBox.setSpacing(50);
        sideVBox.setPadding(new Insets(30,30,30,30));
       // sideVBox.setBackground(new Background(new BackgroundFill(Color.rgb(200, 250, 254), CornerRadii.EMPTY, Insets.EMPTY)));


        buttonToViewState = new HashMap<>();

        accountLabel = new Label("Welcome, " + userAccount.getFirstName());
        accountLabel.setFont(new Font(fontToChoose, 34));
        accountLabel.setPadding(new Insets(20,20,20,20));

        settingsButton = new Button("Settings");
        settingsButton.setFont(new Font(fontToChoose, 18));
        settingsButton.setPrefHeight(60);
        settingsButton.setPrefWidth(100);
        //settingsButton.setPadding(new Insets(10,10,10,10));

        accountSettingsButton = new Button("  View\nAccount");
        accountSettingsButton.setFont(new Font(fontToChoose, 18));
        accountSettingsButton.setPrefHeight(60);
        accountSettingsButton.setPrefWidth(100);
//        settingsButton.setPadding(new Insets(10, 10,10,10));

        signOutButton = new Button("Sign Out");
        signOutButton.setFont(new Font(fontToChoose, 18));
        signOutButton.setPrefHeight(60);
        signOutButton.setPrefWidth(100);
  //      signOutButton.setPadding(new Insets(10,10,10,10));
        signOutButton.setOnAction(this::signOut);

        createGUIForTopHBox();
        createGUIForSideVBox();
        createGUIForBottomHBox();

        populateButtonToViewStateMap();

        buildCentralView(dashBoardButton);

    }

    /**
     * associates all the buttons on the accountPage
     * to a centralView state object value
     */
    private void populateButtonToViewStateMap() {
        buttonToViewState.put(dashBoardButton, new DashBoardViewState(userAccount));
        buttonToViewState.put(viewProjectsButton, new ProjectViewState(userAccount));
        buttonToViewState.put(viewTeamButton, new TeamViewState(userAccount));
        buttonToViewState.put(viewTicketsButton, new TicketsViewState(userAccount, null));
    }

    /**
     * creates GUI for top HBox
     * which includes all the non-main buttons which control the
     * settings part of the application etc.
     */
    private void createGUIForTopHBox() {

        HBox rightButtons = new HBox(settingsButton, accountSettingsButton, signOutButton);
        rightButtons.setAlignment(Pos.CENTER_RIGHT);
     //   rightButtons.setPadding(new Insets(5,5,5,5));

        HBox.setHgrow(rightButtons, Priority.ALWAYS);
        rightButtons.setMinWidth(400);
        rightButtons.setMinHeight(50);
        rightButtons.setSpacing(50);

        System.out.println(topHBox);

        topHBox.getChildren().addAll(accountLabel, rightButtons);
        topHBox.setPadding(new Insets(2,2,2,2));

    //    topHBox.setBackground(new Background(new BackgroundFill(Color.rgb(200, 250, 254), CornerRadii.EMPTY, Insets.EMPTY)));

        mainPane.setTop(topHBox);
        mainPane.setLeft(sideVBox);

    }

    /**
     * creates GUI for bottom HBox
     * which includes the error display pane
     */
    private void createGUIForBottomHBox() {
        ed = new ErrorDisplay();

        ed.addError("Version 1.3.0");

        mainPane.setBottom(ed.getErrorPane());
    }

    /**
     * creates GUI for side VBox
     * which includes all the main buttons which control the
     * bug tracking part of the application
     */
    private void createGUIForSideVBox() {

        int buttonWidth = 150;
        int buttonHeight = 50;

        dashBoardButton = new Button("Dashboard");
        dashBoardButton.setFont(new Font(fontToChoose, 18));
        dashBoardButton.setPrefHeight(buttonHeight);
        dashBoardButton.setPrefWidth(buttonWidth);

        //dashBoardButton.setOnAction(e -> buildCentralView(dashBoardButton));
        dashBoardButton.setOnAction(e -> userAccount.accountToString());


        viewProjectsButton = new Button("View Projects");
        viewProjectsButton.setFont(new Font(fontToChoose, 18));
        viewProjectsButton.setPrefHeight(buttonHeight);
        viewProjectsButton.setPrefWidth(buttonWidth);

        viewProjectsButton.setOnAction(e -> buildCentralView(viewProjectsButton));


        viewTicketsButton = new Button("View Tickets");
        viewTicketsButton.setFont(new Font(fontToChoose, 18));
        viewTicketsButton.setPrefHeight(buttonHeight);
        viewTicketsButton.setPrefWidth(buttonWidth);

        viewTicketsButton.setOnAction(e -> buildCentralView(viewTicketsButton));


        viewTeamButton = new Button("View Team");
        viewTeamButton.setFont(new Font(fontToChoose, 18));
        viewTeamButton.setPrefHeight(buttonHeight);
        viewTeamButton.setPrefWidth(buttonWidth);

        viewTeamButton.setOnAction(e -> buildCentralView(viewTeamButton));




        sideVBox.getChildren().addAll(dashBoardButton, viewProjectsButton, viewTicketsButton, viewTeamButton);
    }


    /**
     * A method that builds the central view according
     * to the map value gained associated with the
     * chosen button
     * @param chosenButton
     */
    private void buildCentralView(Button chosenButton) {

        populateButtonToViewStateMap();

        currentCentralView = buttonToViewState.get(chosenButton);


        currentCentralView.build();

        mainPane.setCenter(currentCentralView.getCentralViewPane());
    }


    /**
     * signes the user out of the application and
     * returns back to the login/signup page
     * @param event
     */
    public void signOut(ActionEvent event) {
        LoginOrSignUpGUI ls = new LoginOrSignUpGUI();
        Scene sc = new Scene(ls.getLSPage(), 1430, 730);
        stage.setScene(sc);
        ls.addStage(stage);
    }


    public BorderPane getEntryMainPane(){
        return mainPane;
    }

    public void addStage(Stage addedStage) {
        stage = addedStage;
    }


}
