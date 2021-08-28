package sample;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;


public class ProjectEditableInfoDisplay extends EditableInfoDisplay {

    private Project project;

    private Label projectNameLabel;                     private EditableLabel projectNameEditable;
    private Label projectDescriptionLabel;              private EditableLabel projectDescriptionLabelEditable;
    private Label projectCreatedByLabel;                private Label projectCreatedByLabelInfo;
    private Label projectDateCreatedLabel;               private Label projectDateCreatedLabelInfo;

    public ProjectEditableInfoDisplay(GridPane gridPaneToDisplayOn, CentralViewState statePageToGoBackTo, Project projectToDisplay, String title) {
        super(gridPaneToDisplayOn, statePageToGoBackTo, title);

        project = projectToDisplay;

        if (project == null) {
            throw new NullPointerException("Project is NUll cannot display in editable display");
        } else {

        projectNameLabel = new Label("Name: ");                projectNameEditable = new EditableLabel(project.getName());
        projectDescriptionLabel = new Label("Description: ");  projectDescriptionLabelEditable = new EditableLabel(project.getDescription());
        projectCreatedByLabel = new Label("Created By: ");     projectCreatedByLabelInfo = new Label(project.getCreatedBy().getFirstName() + " " +  project.getCreatedBy().getLastName());
        projectDateCreatedLabel = new Label("Date Created: "); projectDateCreatedLabelInfo = new Label(project.getDateCreated().toString());
        
        addTitleLabels(projectNameLabel, projectDescriptionLabel,projectCreatedByLabel, projectDateCreatedLabel);
        addEditableLabels(projectNameEditable, projectDescriptionLabelEditable);
        addInfoLabels(projectCreatedByLabelInfo, projectDateCreatedLabelInfo);

        buildEditableInfoDisplay();

        }
    }

    private void fillBasicInfoNodes() {

    }




}