package sample;

import java.util.List;

public class TicketCreator {

    private Project projectToCreateTicketsFor;

    private Priorities priorities;
    private Statuses statuses;

    public TicketCreator(Project projectToCreateTicketsFor) {
        this.projectToCreateTicketsFor = projectToCreateTicketsFor;

        priorities = new Priorities();
        statuses = new Statuses();
    }

    /**
     * Creates a ticket for the associated project
     * @param name
     * @param desc
     * @param priorityWord
     * @param statusWord
     * @param account
     * @param assignedToUserNames
     * @throws AccountNotFoundException
     * @throws AllReadyExistsException
     * @throws EmptyAccountsListException
     * @throws CreatorAccountNullException
     */
    public void createTicketForProject(String name, String desc, Double priorityValDouble, Double statusValDouble, Account account, List<String> assignedToUserNames) throws AccountNotFoundException, AllReadyExistsException, EmptyAccountsListException, CreatorAccountNullException {

        Integer priorityInIntegerForm = priorityValDouble.intValue();
        Integer statusInIntegerForm = statusValDouble.intValue();

        List<Account> listOfAssignedToAccounts = AllCreatedAccounts.getInstance().getListOfAccountsAccordingToEnteredListOfUserNames(assignedToUserNames);

        projectToCreateTicketsFor.createTicketForThisProject(name, desc, priorityInIntegerForm, statusInIntegerForm, account, listOfAssignedToAccounts);


    }


}


