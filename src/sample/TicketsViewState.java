package sample;

import javafx.scene.layout.GridPane;

import java.util.List;

public class TicketsViewState extends CentralViewState {


    private SearchableList ticketSearchableList;

    private AccountExtractor extractor;

    private List<Closable> listOfDisplayCompTickets; //The list of ticketDisplayComps to be displayed

    private TicketDisplayingVisitor ticketDisplayingVisitor;



    public TicketsViewState(Account enteredAccount, GridPane customGridPane) {
        super(enteredAccount, customGridPane );


        extractor = new AccountExtractor(account);
    }

    /**
     * An auxiliary method that extracts a list DisplayComps of the
     * projects the userAccount is partaking in
     */
    private void extractListOfDisplayTicketCompsAccountPartaking() {
        listOfDisplayCompTickets =  extractor.getDisplayCompsListOfAllPartakingTickets();
    }

    @Override
    public void build() {

        extractListOfDisplayTicketCompsAccountPartaking();

        ticketSearchableList = new SearchableList(listOfDisplayCompTickets, "tickets", 800,500);

        mainPane.setCenter(ticketSearchableList.getMainPane());

        insideGridPane.add(mainPane,0,0);

        mainPane.setBottom(errorDisplay.getErrorPane());

        ticketDisplayingVisitor = new TicketDisplayingVisitor(insideGridPane, account, true);

        addVisitorToListOfDisplayCompTickets();

        System.out.println("Grid Pane after building: " + insideGridPane.getChildren().size());

    }


    /**
     * Adds the visitor to each of the display components within the list
     */
    private void addVisitorToListOfDisplayCompTickets() {

        for (Closable comp : listOfDisplayCompTickets) {

            DisplayCompTicket displayCompTicket = (DisplayCompTicket) comp;

            displayCompTicket.acceptTicketDisplayingVisitor(ticketDisplayingVisitor);
        }

    }
}
