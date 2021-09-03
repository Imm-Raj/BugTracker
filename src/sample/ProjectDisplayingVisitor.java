package sample;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

/**
 * A visitor class that has the purpose of extracting information
 * from a project and then putting it into a displayable and editable form
 * to be used when the user opens up a project
 */
public class ProjectDisplayingVisitor extends ClosableVisitor {

    private Project projectToDisplay;

    private GridPane displayPane;

    private Account account;

    public ProjectDisplayingVisitor(GridPane GridPaneToDisplayProjectOn, Account account) {
        super();

        this.account = account;
        displayPane = GridPaneToDisplayProjectOn;
    }

    public void visitDisplayCompProject(DisplayCompProject displayCompProject) {
        projectToDisplay = displayCompProject.getContainedProject();
    }

    public void display() {

        System.out.println("Grid Pane upon display call: " + displayPane.getChildren().size());

        if (projectToDisplay != null) {

            System.out.println("Project Name " + projectToDisplay.getName());
            displayPane.getChildren().clear();

            //displayPane.getChildren().add(new Label("Testing"));

            ProjectEditableInfoDisplay p = new ProjectEditableInfoDisplay(displayPane,  new ProjectViewState(account), projectToDisplay, "View Project", account);

        }


    }

}
