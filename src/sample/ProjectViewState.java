package sample;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class ProjectViewState extends CentralViewState {

    private Button createNewProjectButton;

    private SearchableList projectSearchableList;

    private AccountExtractor extractor;

    private VBox sideVBox;

    private List<Closable> listOfDisplayCompProjects; //The list of projectDisplayComps to be displayed

    private ProjectDisplayingVisitor projectDisplayingVisitor;

    public ProjectViewState(Account enteredAccount) {

        super(enteredAccount, null);


        sideVBox = new VBox();
        sideVBox.setPadding(new Insets(10,10,10,10));

        createNewProjectButton = new Button("+ Project");

        customiser.addLabelledNodeToDefaultCustomisation(createNewProjectButton);
        createNewProjectButton.setOnAction(this::openCreateProjectPage);


        extractor = new AccountExtractor(account);





       // labeledNodeList.add(createNewProjectButton);

        //customiseLabeledNodes();

    }

    public AccountExtractor getExtractor() {
        return extractor;
    }

    /**
     * An auxiliary method that extracts a list DisplayComps of the
     * projects the userAccount is partaking in
     */
    private void extractListOfDisplayProjectCompsAccountPartaking() {
        listOfDisplayCompProjects =  extractor.getDisplayCompProjectsOfPartakingProjects();
    }

    @Override
    public void build() {

        extractListOfDisplayProjectCompsAccountPartaking();

        projectSearchableList = new SearchableList(listOfDisplayCompProjects, "projects", 800,500);

        mainPane.setCenter(projectSearchableList.getMainPane());

        insideGridPane.add(mainPane,0,0);

        mainPane.setRight(sideVBox);

        sideVBox.getChildren().add(createNewProjectButton);

        mainPane.setBottom(errorDisplay.getErrorPane());

        projectDisplayingVisitor = new ProjectDisplayingVisitor(insideGridPane, account);

        addVisitorToListOfDisplayCompProjects();

        System.out.println("Gird Pane after building: " + insideGridPane.getChildren().size());

    }

    public void openCreateProjectPage(ActionEvent event)  {

        insideGridPane.getChildren().clear();

        CreateProjectPage createProjectPage = new CreateProjectPage(insideGridPane, account);

        insideGridPane.add(createProjectPage.getMainProjectPane(),0,0);

    }

    /**
     * Adds the visitor to each of the display components within the list
     */
    private void addVisitorToListOfDisplayCompProjects() {

        for (Closable comp : listOfDisplayCompProjects) {

            DisplayCompProject displayCompProject = (DisplayCompProject) comp;

            displayCompProject.acceptProjectDisplayingVisitor(projectDisplayingVisitor);
        }

    }


//    private List<Closable> constructSearchList() {
//
//        // List<Closable> listOfComp = extractor.getDisplayCompProjectsOfPartakingProjects();
//
//
//        List<Closable> listOfComp = new ArrayList<>();
//
//        String[] positionsAcc =  {"Junior Developer", "Analyst", "Tester"};
//
//        Account accCreator = null;
//        try {
//            accCreator = new Account("J123", "H45098tj","James", "Harrison", 2003, 7, 3, positionsAcc );
//        } catch (PositionNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        Account acc1 = null;
//        try {
//            acc1 = new Account("J1", "1","Mat  ", "haros", 2003, 7, 3, positionsAcc );
//        } catch (PositionNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        Account acc2 = null;
//        try {
//            acc2 = new Account("j2", "2","Sam", "Jamesh", 2003, 7, 3, positionsAcc );
//        } catch (PositionNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        ArrayList<Account> dList = new ArrayList<>();
//        dList.add(accCreator);
//        dList.add(acc1);
//        dList.add(acc2);
//
//        try {
//
//            AllCreatedProjects ac = AllCreatedProjects.getInstance();
//
//            ac.addProject("Traxerr", "g", accCreator, dList );
//            ac.addProject("Fifa", "g", accCreator, dList );
//            ac.addProject("Rooker", "g", accCreator, dList );
//            ac.addProject("Vinel", "g", accCreator, dList );
//            ac.addProject("Bug4none", "g", accCreator, dList );
//            ac.addProject("Samlot", "g", accCreator, dList );
//            ac.addProject("piksmeg", "g", accCreator, dList );
//            ac.addProject("havenla", "g", accCreator, dList );
//            ac.addProject("track", "g", accCreator, dList );
//
//            for (Project p : ac.getProjectsList()) {
//                Closable displayCompProj = new DisplayCompProject(p);
//                listOfComp.add(displayCompProj);
//            }
//
//
//        } catch (EmptyAccountsListException e) {
//            e.printStackTrace();
//        } catch (CreatorAccountNullException e) {
//            e.printStackTrace();
//        } catch (AllReadyExistsException e) {
//            e.printStackTrace();
//        }
//
//        return listOfComp;
//    }



}
