package sample;

import java.time.LocalDate;
import java.util.List;

public class Ticket {

    private String name;
    private String ticketID;
    private LocalDate dateCreated;
    private String description;
    private List<Comment> comments;
    private List<Account> assignedTo;
    private int priority;
    private int status;
    private Project associatedProject;
    private Account createdBy;
    private LocalDate dateClosed;
    private boolean isOpen;

    private boolean isComplete;
    private String accountIDCompletedBy;

    private Priorities priorities;
    private Statuses statuses;


    /**
     * Checks that the parameters are entered correctly, checks that the creator account is not null
     * Checks that the assignedTo developers are not null or of size 0
     * Also checks priority and status parameters
     * Adds the ticket to each corresponding ticket
     * @param name
     * @param singleTicketEnteredID
     * @param description
     * @param priority
     * @param associatedProject
     * @param associatedProjectID
     * @param currentStatus
     * @param createdBy
     * @param assignedTo
     * @throws PriorityNotFoundException
     * @throws StatusNotFoundException
     * @throws CreatorAccountNullException
     * @throws EmptyAccountsListException
     */
    public Ticket(String name, String singleTicketEnteredID, String description, int priority, Project associatedProject, String associatedProjectID, int currentStatus, Account createdBy, List<Account> assignedTo)
            throws PriorityNotFoundException, StatusNotFoundException, CreatorAccountNullException, EmptyAccountsListException {

        priorities = new Priorities();

        statuses = new Statuses();

        if (createdBy == null) {

            throw new CreatorAccountNullException();

        } else if (assignedTo == null || assignedTo.size() == 0) {

            throw  new EmptyAccountsListException();

        } else if (!priorities.validatePriorityValue(priority)) {

            throw new PriorityNotFoundException("" + priority);

        } else if (!statuses.validateStatusValue(currentStatus)) {

            throw new StatusNotFoundException("" + currentStatus);
        } else {

            this.name = name;
            ticketID = createTicketID(associatedProjectID, singleTicketEnteredID);
            this.description = description;
            this.priority = priority;
            this.associatedProject = associatedProject;
            this.status = currentStatus;
            this.assignedTo = assignedTo;
            dateCreated = LocalDate.now();
            this.createdBy = createdBy;
            isOpen = true;
            isComplete = false;

            createdBy.addTicketIDtoAllCreatedTicketsID(ticketID);

            for (Account a : assignedTo) {
                a.addTicketIDtoPartakingTickets(ticketID);
            }

        }


    }

    public String getName() {
        return name;
    }

    public String getTicketId() {
        return ticketID;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public String getDescription() {
        return description;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public List<Account> getAssignedTo() {
        return assignedTo;
    }

    public int getPriority() {
        return priority;
    }

    public Project getAssociatedProject() {
        return associatedProject;
    }

    public int getStatus() {
        return status;
    }

    public LocalDate getDateClosed() {
        return dateClosed;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public Account getCreatedBy() {
        return createdBy;
    }


    /**
     * An auxillery method that joins the project ID and the partial
     * ticket ID to return a unique ticket ID for every single ticket
     * @param projectID
     * @param partialTicketID
     * @return String unique ticket ID
     */
    private String createTicketID(String projectID, String partialTicketID) {
        return projectID + " - " + partialTicketID;
    }

    /**
     * A method that changes the priority of the ticket
     * if the entered new priority value is invalid
     * then an exception is thrown
     * @param newPriorityVal
     * @throws PriorityNotFoundException
     */
    public void changePriority(int newPriorityVal) throws PriorityNotFoundException {
        if (priorities.validatePriorityValue(newPriorityVal)) {
            priority = newPriorityVal;
        } else {
            throw new PriorityNotFoundException(" " + newPriorityVal);
        }
    }

    /**
     * A method that changes the status of the ticket
     * if the entered new status value is invalid
     * then an exception is thrown
     * @param newStatusVal
     * @throws StatusNotFoundException
     */
    public void changeStatus(int newStatusVal) throws StatusNotFoundException {
        if (priorities.validatePriorityValue(newStatusVal)) {
            status = newStatusVal;
        } else {
            throw new StatusNotFoundException(" " + newStatusVal);
        }
    }

    /**
     * A method that closes the ticket by adding a closing date
     * and removing the ticket from each of the partaking developers accounts
     */
   public void close() {
        dateClosed = LocalDate.now();
        isOpen = false;

        for (Account a : assignedTo) {
            a.removePartakingTicketFromAccount(ticketID);
        }

   }

    /**
     * Completes the ticket and adds info for whoever
     * completed it to the fields of the ticket
     * @param completedByAccountID
     */
   public void completeTicket(String completedByAccountID) {
       isComplete = true;
       accountIDCompletedBy = completedByAccountID;
       close();
   }


    /**
     * Checks if a given username is a participant of a ticket
     * @param userName
     * @return
     */
    public boolean checkIfNameIsParticipantOfTicket(String userName) {

        boolean result = false;

        for (Account a : assignedTo) {
            if (a.getUserName().equals(userName)) {
                result = true;
            }
        }

        return result;
    }

    public void updateTicketInfo(String newTicketName, String newTicketDescription, List<Account> newAssignedTo, double priorityDoubleVal, double statusDoubleVal) {

        if (newTicketName == null || newTicketDescription.length() == 0) {
            throw new NullPointerException("Project Name invalid");
        } else if (newTicketDescription == null || newTicketDescription.length() == 0) {
            throw new NullPointerException("Project Description invalid");
        } else if (newAssignedTo == null || newAssignedTo.size() == 0) {
            throw new IllegalArgumentException("Please select a participant");
        } else {

            for (Account a : assignedTo) {
                a.removePartakingTicketFromAccount(ticketID);
            }

            name = newTicketName;
            description = newTicketDescription;
            assignedTo = newAssignedTo;
            priority = (int) priorityDoubleVal;
            status = (int) statusDoubleVal;

            //After participants field has been updated
            //Add the project to the accounts of the new participants
            for (Account a : assignedTo) {
                a.addTicketIDtoPartakingTickets(ticketID);
            }
        }
    }
}
