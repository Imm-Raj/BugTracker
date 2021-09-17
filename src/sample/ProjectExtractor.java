package sample;

import java.util.ArrayList;
import java.util.List;

/**
 * Responsible for extracting information from a specific project
 */
public class ProjectExtractor extends Extractor  {

    private Project project;

    public ProjectExtractor(Project project) {
        this.project = project;
    }

    /**
     *
     * @return returns a list of all tickets from the project
     */
    private List<Ticket> extractAllTicketsFromProject() {

        List<Ticket> ticketList = new ArrayList<>();

        for (Ticket t : project.getListOfTickets()) {
            ticketList.add(t);
        }

        return ticketList;

    }

    public List<Closable> getDisplayCompOfAllTicketsInProject() {

        List<Ticket> ticketList = extractAllTicketsFromProject();

        List<Closable> displayCompTicketsList = new ArrayList<>();

            for (Ticket t : ticketList) {
                displayCompTicketsList.add(new DisplayCompTicket(t));
            }

        return displayCompTicketsList;

    }

    /**
     * @param account
     * @return A list of all the tickets that the account is participating in within the project
     */
    public List<Closable> getDisplayCompOfAllTicketsAssignedToAccountWithinProject(Account account) {

        List<String> idOfTicketsOfAccounts = account.getAllTicketsPartaking();
        List<Ticket> ticketList = extractAllTicketsFromProject();

        List<Closable> displayCompTicketsListOfProjectParticipatingTickets = new ArrayList<>();

        for (String ticketID : idOfTicketsOfAccounts) {
            for (Ticket ticket : ticketList) {
                if (ticketID.equals(ticket.getTicketId())) {
                    displayCompTicketsListOfProjectParticipatingTickets.add(new DisplayCompTicket(ticket));
                }
            }
        }

        return displayCompTicketsListOfProjectParticipatingTickets;

    }


}
