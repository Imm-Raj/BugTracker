package sample;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class that extracts projects and tickets from an individual account
 * based on the list of ID string stored within the account
 * converts them into Display Components
 */
public class AccountExtractor extends Extractor {

    private Account sourceAccount; // The account from which items will be extracted

    private AllCreatedProjects allCreatedProjects;

    private AllCreatedAccounts allCreatedAccounts;


    public AccountExtractor(Account sourceAccount) {
        this.sourceAccount = sourceAccount;

        allCreatedProjects = AllCreatedProjects.getInstance();
        allCreatedAccounts = AllCreatedAccounts.getInstance();

    }


    public Account getSourceAccount() {
        return sourceAccount;
    }

    public AllCreatedProjects getAllCreatedProjects() {
        return allCreatedProjects;
    }

    /**
     *
     * @return DisplayComponents of all projects that the account
     * is participating in
     */
    public List<Closable> getDisplayCompProjectsOfPartakingProjects() {

        List<Project> partakingProjects = extractListOfPartakingProjectsFromAccount();

        System.out.println("Partaking Projects COMPS of " + sourceAccount.getUserName() + ": " + createListOfDisplayCompProjectsFromListOfProjects(partakingProjects).size());

        return createListOfDisplayCompProjectsFromListOfProjects(partakingProjects);
    }

    /**
     *
     * @return DisplayComponents of all projects that the account
     * has created
     */
    public List<Closable> getDisplayCompProjectsOfCreatedProjects() {

        List<Project> createdProjects = extractListOfCreatedProjectsFromAccount();

        return createListOfDisplayCompProjectsFromListOfProjects(createdProjects);
    }


    /**
     *
     * @return a List of projects that the account is participating in
     * based on the project IDs stored
     * within the accounts partakingProjectIDs field
     */
    private List<Project> extractListOfPartakingProjectsFromAccount() {

        List<String> accountPartakingProjectIDs = sourceAccount.getAllProjectsPartaking();

        return getProjects(accountPartakingProjectIDs);

    }

    /**
     *
     * @return a List of projects that the account has created
     * based on the project IDs stored
     * within the accounts partakingProjectIDs field
     */
    private List<Project> extractListOfCreatedProjectsFromAccount() {

        List<String> accountCreatedProjectIDs = sourceAccount.getAllProjectsCreated();

        return getProjects(accountCreatedProjectIDs);

    }

    private List<Project> getProjects(List<String> enteredProjectIDs) {
        List<Project> allProjects = allCreatedProjects.getProjectsList();

        List<Project> extractedProjects = new ArrayList<>();

        for (String id : enteredProjectIDs) {
            for (Project p : allProjects) {
                if (p.getProjectID().equals(id)) {
                    extractedProjects.add(p);
                }
            }
        }

        return extractedProjects;
    }

    /**
     * @param projectsList
     * @return A list of display components that represent the projects entered
     */
    private List<Closable> createListOfDisplayCompProjectsFromListOfProjects(List<Project> projectsList) {

        List<Closable> displayCompProjectsList = new ArrayList<>();

        for (Project p : projectsList) {
            displayCompProjectsList.add(convertProjectToProjectDisplayComponent(p));
        }

        return displayCompProjectsList;
    }

    /**
     * Converts a given project into a project display component
     * @param projectToConvert
     * @return
     */
    private Closable convertProjectToProjectDisplayComponent(Project projectToConvert) {
        return new DisplayCompProject(projectToConvert);
    }


    /**
     * extracts a list of corresponding accounts according
     * to the list of userNames entered
     * @param userNamesList
     * @return
     */
    public List<Account> extractAccountsListFromUserNameList(List<String> userNamesList) {
        return userNamesList.stream().map(u -> extractAccount(u)).collect(Collectors.toList());
    }

    private Account extractAccount(String userName) {

        Account resultAccount = null;

        for (Account a : allCreatedAccounts.getAccountsList()) {
            if (userName.equals(a.getUserName())) {
                resultAccount = a;
            }
        }

        return resultAccount;
    }

    /**
     *
     * @return DisplayComponents of all tickets that the account
     *      * is participating in
     */
    public List<Closable> getDisplayCompsListOfAllPartakingTickets() {
        List<Ticket> listOfPartakingTickets = new ArrayList<>();

        List<String> listOfAccountPartakingTicketIDs = sourceAccount.getAllTicketsPartaking();
        
        List<Ticket> allTicketsFromPartakingProjects = getTicketsFromAllPartakingProjects();
        
        for (Ticket t  : allTicketsFromPartakingProjects) {
            for (String ticketID : listOfAccountPartakingTicketIDs) {
                if (t.getTicketId().equals(ticketID)) {
                    listOfPartakingTickets.add(t);
                }
            }
        }

        return createListOfDisplayCompTicketsFromListOfTickets(listOfPartakingTickets);
        
        
    }

    /**
     * @param ticketList
     * @return A list of display components that represent the projects entered
     */
    private List<Closable> createListOfDisplayCompTicketsFromListOfTickets(List<Ticket> ticketList) {

        List<Closable> displayCompTicketsList = new ArrayList<>();

        for (Ticket t : ticketList) {
            displayCompTicketsList.add(convertTicketToTicketDisplayComponent(t));
        }

        return displayCompTicketsList;
    }

    private Closable convertTicketToTicketDisplayComponent(Ticket t) {
        return new DisplayCompTicket(t);
    }


    private List<Ticket> getTicketsFromAllPartakingProjects() {
        
        List<Project> listOfAllProjectsPartaking = extractListOfPartakingProjectsFromAccount();
        
        List<Ticket> listOfAllPartakingProjectTickets = new ArrayList<>();
        
        for (Project p : listOfAllProjectsPartaking) {
          listOfAllPartakingProjectTickets.addAll(p.getListOfTickets());
        }
        
        return listOfAllPartakingProjectTickets;
    }

    private List<Ticket> getListOfEveryTicket() {
        AllCreatedProjects allCreatedProjects = AllCreatedProjects.getInstance();
        
        List<Ticket> allTickets = new LinkedList<>();
        
        for (Project p : allCreatedProjects.getProjectsList()) {
           allTickets.addAll(p.getListOfTickets());
        }
        
        return allTickets;
    }
}
