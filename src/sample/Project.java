package sample;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;


public class Project {

    private String name;
    private String projectID;
    private String description;
    private LocalDate dateCreated;
    private LocalDate dateClosed;
    private boolean isOpen;
    private Account createdBy;
    private List<Account> developers;

    private List<Ticket> listOfTickets; // A collection of tickets that the project has

    private int currentTicketID; // An auxiliary id provider that increments each time a ticket is made
                                // so that each ticket has a unique part id


    /**
     * Constructor creates the project
     * If the created Account is null throws exception
     * if the list of enteredDevelopers is null or has a size of 0 throws exception
     * Adds the project to each corresponding Account
     * @param name
     * @param enteredProjectID
     * @param description
     * @param createdBy
     * @param enteredDevelopers
     * @throws EmptyAccountsListException
     * @throws CreatorAccountNullException
     */
    public Project(String name, int enteredProjectID, String description, Account createdBy, List<Account> enteredDevelopers)
            throws EmptyAccountsListException, CreatorAccountNullException {

        if (enteredDevelopers == null || enteredDevelopers.size() == 0) {

            throw new EmptyAccountsListException();

        } else if (createdBy == null) {

            throw new CreatorAccountNullException();

        } else {
            this.name = name;
            projectID = "" + enteredProjectID;
            this.description = description;
            this.createdBy = createdBy;
            developers = enteredDevelopers;

            dateCreated = LocalDate.now();

            listOfTickets = new ArrayList<>();

            createdBy.addProjectIDToAllCreatedProjectsID(projectID);

            for (Account a : developers) {
                a.addProjectIDtoPartakingProjects(projectID);
            }

            currentTicketID = 0;

            isOpen = true;
        }

    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public Account getCreatedBy() {
        return createdBy;
    }

    public List<Ticket> getListOfTickets() {
        return listOfTickets;
    }

    public String getProjectID() {
        return projectID;
    }

    public List<Account> getDevelopers() {
        return developers;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public LocalDate isDateClosed() {
        return dateClosed;
    }

    public int getCurrentTicketID() {
        return currentTicketID;
    }

    /**
     * Creates a ticket for this project and adds it to the
     * list of tickets owned by this project checks for any exceptions thrown
     * @param name
     * @param description
     * @param priority
     * @param currentStatus
     * @param createdBy
     * @param assignedTo
     * @throws AllReadyExistsException
     * @throws CreatorAccountNullException
     * @throws EmptyAccountsListException
     */
    public void createTicketForThisProject(String name, String description, int priority, int currentStatus, Account createdBy, List<Account> assignedTo)
            throws AllReadyExistsException, CreatorAccountNullException, EmptyAccountsListException {


        if (!checkForTicketsWithSameName(name)) {

            try {
                Ticket newTicket = new Ticket(name, "" +  currentTicketID , description, priority, this, projectID, currentStatus, createdBy, assignedTo);
                listOfTickets.add(newTicket);
                currentTicketID++;
            } catch (PriorityNotFoundException e) {
                System.out.println(e.getMessage());
            } catch (StatusNotFoundException e) {
                System.out.println(e.getMessage());
            }

        } else {
            throw new AllReadyExistsException(name);
        }

    }

    /**
     * A method that removes a selected ticket (Through ticket ID)
     * and removes it. Utilises an iterator and uses some casting
     * throws an exception if the ticket is not found
     * @param idOfTicketToRemove
     * @throws IllegalAccessException
     */
    public void removeTicketFromThisProject(String idOfTicketToRemove) throws IllegalArgumentException {

        boolean correctID = false;
        Iterator it = listOfTickets.iterator();

        while (it.hasNext()) {
            Ticket ticket = (Ticket) it.next();
            if (ticket.getTicketId().equals(idOfTicketToRemove)) {
                ticket.close();
                it.remove();
                correctID = true;
            }
        }

        if (!correctID) {
            throw new IllegalArgumentException("The ticket with ID: " + idOfTicketToRemove + " is not there");
        }
    }

    /**
     * A method that checks for if a Ticket exists with
     * the same name - this should not exist
     * @param enteredName
     * @return true if Ticket exists with same name, false otherwise
     */
    private boolean checkForTicketsWithSameName(String enteredName) {

        boolean result = false;
        for (Ticket t : listOfTickets) {
            if (t.getName().equals(enteredName)) {
                result = true;
            }
        }

        return result;
    }

    public Ticket getTicketUsingID(String id) {

        Ticket ticketChosen = null;
     //   boolean result = false;

        for (Ticket t : listOfTickets) {
            if (t.getTicketId().equals(id)) {
              //  result = true;
                ticketChosen = t;
            }
        }

//        if (result) {
//            return ticketChosen;
//        } else {
//            throw new NullPointerException("Ticket not found");
//        }

        return ticketChosen;
    }

    public void printProjectInfo() {

            System.out.println();
            System.out.println(getName());
            System.out.println(getProjectID());
            System.out.println(getDateCreated());
            System.out.println("List of Tickets: " + getTicketsListString());

    }

    /**
     * A testing method that returns a string
     * representing some information of the project
     * @return String
     */
    public String getTicketsListString() {
        String ticketString = "";
        for (Ticket t : listOfTickets) {
            ticketString+= ", " + t.getTicketId() +  "(STATUS: " + t.getCurrentStatus() + ")" + "(PRIORITY: " + t.getPriority() + ")";
        }

        return ticketString;
    }


    /**
     * Closes the project by setting the isOpen flag
     * to false and setting the closing date.
     * All developers that were partaking will
     * have the project ID removed from their account
     * All tickets that were within the closed project will be closed as well
     */
    public void close() {

        dateClosed = LocalDate.now();
        isOpen = false;

        for (Account a : developers) {
            a.removePartakingProjectFromAccount(projectID);
        }

        if (listOfTickets.size() > 0) {
            for (Ticket t : listOfTickets) {
                t.close();
            }
        }

    }

}



