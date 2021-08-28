package sample;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that creates the project and adds it the creator account
 * encapsulates project creation
 */
public class ProjectCreator {

    private AllCreatedProjects allCreatedProjects;
    private AllCreatedAccounts allCreatedAccounts;

    private Account accountCreatingProject;

    private ErrorDisplay errorDisplay;

    public ProjectCreator(Account account, ErrorDisplay errorDisplay) {

        accountCreatingProject = account;

        errorDisplay = errorDisplay;

        allCreatedAccounts = AllCreatedAccounts.getInstance();
        allCreatedProjects = AllCreatedProjects.getInstance();

    }

    public AllCreatedProjects getAllCreatedProjects() {
        return allCreatedProjects;
    }

    public AllCreatedAccounts getAllCreatedAccounts() {
        return allCreatedAccounts;
    }

    /**
     * Creates the project by taking in entered information
     * @param listOfUserNamesOfPartakers
     * @param creatorAccount
     * @param projectName
     * @param projectDescription
     * @throws AllReadyExistsException
     * @throws EmptyAccountsListException
     * @throws CreatorAccountNullException
     */
    public void createProject(List<String> listOfUserNamesOfPartakers, Account creatorAccount, String projectName, String projectDescription)
            throws AllReadyExistsException, EmptyAccountsListException, CreatorAccountNullException {

           List<Account> participatingDevelopers = retrieveAccountsFromDeveloperNames(listOfUserNamesOfPartakers);


            allCreatedProjects.addProject(projectName, projectDescription, accountCreatingProject, participatingDevelopers);

    }

    /**
     * Retrieves all the accounts by using enteredUserName list
     * @param listOfUserNamesOfPartakers
     * @return
     */
    private List<Account> retrieveAccountsFromDeveloperNames(List<String> listOfUserNamesOfPartakers )  {
        List<Account> allAccountsOfPartakers = new ArrayList<>();

        for (String userName : listOfUserNamesOfPartakers) {
            try {

                allAccountsOfPartakers.add(allCreatedAccounts.findAccountUsingUserName(userName));

            } catch (AccountNotFoundException e) {
                errorDisplay.addError("ERROR: ACCOUNT HAS NOT BEEN FOUND WITH USERNAME" +
                        "\n CONTACT SYSTEM ADMIN");
            } catch (NullPointerException e) {
                errorDisplay.addError("ERROR: USERNAME ENTERED IS NULL" +
                        "\n  CONTACT SYSTEM ADMIN");
            }
        }

        return allAccountsOfPartakers;
    }


}
