package sample;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import java.util.stream.Collectors;

public class DisplayCompProject extends Closable {

    private Project containedProject;

    private VBox leftVbox;

    private Label nameLabel;
    private Label createdByLabel;
    private Label noDevelopersLabel;
    private Label noTicketsLabel;

    private  BorderPane rightPane;

    private Label idLabel;

    private Button openButton;

    private ProjectDisplayingVisitor visitor;

    public DisplayCompProject(Project enteredProject) {

        containedProject = enteredProject;

        leftVbox = new VBox();

        rightPane = new BorderPane();

        name = containedProject.getName();

        if (enteredProject.isOpen()) {
            setOpen();
        } else {
            setClose();
        }

        openButton = new Button("Open");

        openButton.setOnAction(this::open);


        build();

    }

    public Project getContainedProject() {
        return containedProject;
    }

    /**
     * Fills out the information by extracting it from the contained project
     * Also sets font and resizes the labels/Buttons
     */
    private void fillInfo() {

        nameLabel = new Label(containedProject.getName());

        nameLabel.setFont(Font.font("FiraGo", FontWeight.BOLD , 20));

        createdByLabel = new Label("Created By: " + containedProject.getCreatedBy().getFirstName() + " " +  containedProject.getCreatedBy().getLastName());
        createdByLabel.setPadding(new Insets(10,10,10,10));

        noDevelopersLabel = new Label("Developers: " + containedProject.getDevelopers().size());
        noDevelopersLabel.setPadding(new Insets(10,10,10,10));

        noTicketsLabel = new Label("Active Tickets: "  + containedProject.getListOfTickets().stream().filter(
                t -> t.isOpen()).collect(Collectors.toList()).size()
        );

        noTicketsLabel.setPadding(new Insets(10,10,10,10));

        idLabel = new Label("ID: " + containedProject.getProjectID());

        idLabel.setFont(Font.font("Verdana", FontPosture.ITALIC, 18));

        idLabel.setTextFill(Color.GREY);

        addAllLabeledNodestoLabeledList(createdByLabel, noDevelopersLabel, noTicketsLabel, openButton);

        updateFontOfLabeledNodes("FiraGo", 16);

    }

    /**
     * Method responsible for putting all the information components
     * together and filling the information
     */
    private void build() {
        fillInfo();

        leftVbox.getChildren().addAll(nameLabel, createdByLabel, noDevelopersLabel, noTicketsLabel);

        rightPane.setTop(idLabel);

        rightPane.setBottom(openButton);

        mainPane.setLeft(leftVbox);
        mainPane.setRight(rightPane);

    }

    public void acceptProjectDisplayingVisitor(ProjectDisplayingVisitor projectDisplayingVisitor) {

        visitor = projectDisplayingVisitor;
    }

    private void open(ActionEvent event) {

        visitor.visitDisplayCompProject(this);
        visitor.display();

    }


}
