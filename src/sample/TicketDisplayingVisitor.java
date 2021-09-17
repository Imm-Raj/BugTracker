package sample;

import javafx.scene.layout.GridPane;

public class TicketDisplayingVisitor {

    private Ticket ticketToDisplay;

    private GridPane displayPane;

    private Account account;

    private boolean isViewOnly;

    public TicketDisplayingVisitor(GridPane GridPaneToDisplayTicketOn, Account account, boolean isViewOnly) {
        super();

        this.account = account;
        displayPane = GridPaneToDisplayTicketOn;
        this.isViewOnly = isViewOnly;
        
    }


    public void visitDisplayCompTicket(DisplayCompTicket displayCompTicket) {
        ticketToDisplay = displayCompTicket.getContainedTicket();
    }

    public void display() {

        if (ticketToDisplay != null) {

            displayPane.getChildren().clear();

            TicketEditableInfoDisplay t = new TicketEditableInfoDisplay(displayPane, ticketToDisplay, ticketToDisplay.getAssociatedProject(), "View Ticket", account, isViewOnly);
        }


    }
}
