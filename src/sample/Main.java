
package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;


 public class Main extends Application {



    private Scene scene;


    @Override
    public void start(Stage primaryStage) throws Exception{


        LoginOrSignUpGUI ls = new LoginOrSignUpGUI();

        scene = new Scene(ls.getLSPage(), 1430,730);

        primaryStage.setTitle("TraxErr");

        primaryStage.setScene(scene);

        ls.addStage(primaryStage);

         //JavaFX must have a Scene (window content) inside a Stage (window)

        createAndFillAccounts();


        System.out.println(primaryStage);

        System.out.println(scene.toString());

        //Show the Stage (window)
        primaryStage.show();


    }

//
//public class Main extends Application {
//
//
//
//   private static AllCreatedAccounts aca;
//
//    private Scene scene;
//
//
//    @Override
//    public void start(Stage primaryStage) throws Exception{
//
//        VBox mainBox = new VBox();
//
//        createAndFillAccounts();
//
//        AllCreatedProjects allCreatedProjects = AllCreatedProjects.getInstance();
//
//        Account mainAcc = aca.findAccountUsingUserName("Julian123");
//
//        allCreatedProjects.addProject("TraxErr", "This is cool", mainAcc, aca.getAccountsList() );
//
//        allCreatedProjects.getProjectFromList("0").createTicketForThisProject("First Ticket", "This is a very first ticket" +
//                "", 1,1, mainAcc, aca.getAccountsList());
//
//        allCreatedProjects.getProjectFromList("0").printProjectInfo();
//
//        Ticket t = allCreatedProjects.getProjectFromList("0").getTicketUsingID("0 - 0");
//
//        DisplayCompTicket displayCompTicket = new DisplayCompTicket(t);
//
//        List<Closable> compList = new ArrayList<>();
//
//        compList.add(displayCompTicket);
//
//        SearchableList searchableList = new SearchableList(compList, "Tickets", 500,500);
//
//
//        mainBox.getChildren().add(searchableList.getMainPane());
//
//        scene = new Scene(mainBox, 1430,730);
//
//        primaryStage.setTitle("TraxErr");
//
//        primaryStage.setScene(scene);
//
//
//
//        //JavaFX must have a Scene (window content) inside a Stage (window)
//
//
//
//
//
//        //Show the Stage (window)
//        primaryStage.show();
//
//
//    }

    public static void main(String[] args) {
        launch(args);
    }
//
    public static void createAndFillAccounts() {

        AllCreatedAccounts   aca = AllCreatedAccounts.getInstance();

                        String[] selectionOne = {"Developer", "Tester"};
                        String[] selectionTwo = {"Analyst"};
                        String[] selectionThree = {"Junior Developer"};
                        String[] selectionFour = {"Developer"};

                        String[] empty = {};

                        String[] selectionOneAdmin = {"Senior Developer"};
                        String[] selectionTwoAdmin = {"Project Manager"};
                        String[] selectionThreeAdmin = {"Senior Developer, Team Leader"};


        try {
            aca.addAccount("Julian123", "j123", "Julia", "Darrel", 1972,8 ,23, selectionOne, empty);
            aca.addAccount("Harry123", "h123", "Harry", "Garrod", 1992,8 ,13, selectionOne, empty);
            aca.addAccount("Franklin123", "f123", "Franklin", "Wong", 1968,4 ,13, empty, selectionTwoAdmin );

            aca.addAccount("Adrian123", "a123", "Adrian", "Parker", 1998,1 ,13, selectionFour, empty);
            aca.addAccount("Caly", "c123", "Caly", "Cheng", 2000,4 ,13, empty, selectionTwoAdmin );
        } catch (PositionNotFoundException e) {
            e.printStackTrace();
        } catch (AdminPositionNotFoundException e) {
            e.printStackTrace();
        } catch (DuplicateUsernameException e) {
            e.printStackTrace();
        } catch (DuplicatePasswordException e) {
            e.printStackTrace();
        }

    }
 }




