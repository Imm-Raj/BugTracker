package sample;

import javafx.event.ActionEvent;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;

public class DisplayCompTicket extends Closable {

    private Ticket containedTicket;

    private VBox leftVbox;

    private VBox rightVbox;

    private Label nameLabel;
    private Label createdByLabel;
    private Label dateCreatedLabel;
    private Label numberWorkingOnLabel;
    private Label priorityLabel;
    private Label statusLabel;


    private BorderPane rightPane;

    private Label idLabel;

    private Button openButton;


    public DisplayCompTicket(Ticket enteredTicket) {

        containedTicket = enteredTicket;

        leftVbox = new VBox();
        rightVbox = new VBox();
        rightPane = new BorderPane();

        name = containedTicket.getName();
        
        if (enteredTicket.isOpen()) {
            setOpen();
        } else {
            setClose();
        }

        openButton = new Button("Open");

        openButton.setOnAction(this::open);


        build();
    }

    public Ticket getContainedTicket() {
        return containedTicket;
    }

    private void fillInfo() {

        Priorities priorities = new Priorities();
        Statuses statuses = new Statuses();

        nameLabel = new Label("Name: " + containedTicket.getName());
        createdByLabel = new Label("Created By: " + containedTicket.getCreatedBy().getFirstName() + " " +  containedTicket.getCreatedBy().getLastName());
        dateCreatedLabel = new Label("Date Created: " + containedTicket.getDateCreated().toString());
        numberWorkingOnLabel = new Label("Participants: " + containedTicket.getAssignedTo().size());
        priorityLabel = new Label("Priority: " + priorities.getPriorityInWordForm(containedTicket.getPriority()));
        statusLabel = new Label("Status: " + statuses.getStatusInWordForm(containedTicket.getCurrentStatus()));

        idLabel = new Label("ID: " + containedTicket.getTicketId());
        idLabel.setFont(Font.font("Verdana", FontPosture.ITALIC, 18));

        idLabel.setTextFill(Color.GREY);

        addAllLabeledNodestoLabeledList(createdByLabel, nameLabel, dateCreatedLabel, numberWorkingOnLabel, openButton, statusLabel, priorityLabel);

        updateFontOfLabeledNodes("FiraGo", 16);

    }

    private void build() {
        fillInfo();

        leftVbox.getChildren().addAll(nameLabel, numberWorkingOnLabel, statusLabel, priorityLabel);

        rightVbox.getChildren().addAll(idLabel,createdByLabel, dateCreatedLabel);

        rightPane.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

        rightPane.setTop(rightVbox);

        rightPane.setBottom(openButton);

        mainPane.setLeft(leftVbox);
        mainPane.setRight(rightPane);

    }

    /**
     * Opens the component by setting isOpen flag to true and
     * changes the background colour of the main pane of the component to ____
     * @Override
     */
    public void setOpen() {
        isOpen = true;

        String enteredByUser = "abcdef";
        mainPane.setStyle("-fx-background-color: rgba(255,151,37,0.42)rgba(255,151,37,0.56)#" + enteredByUser);

    }

    private void open(ActionEvent actionEvent) {
    }
}
