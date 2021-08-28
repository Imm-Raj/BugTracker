package sample;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that extracts projects and tickets from an individual account
 * based on the list of ID string stored within the account
 * converts them into Display Components
 */
public class AccountExtractor {

    private Account sourceAccount; // The account from which items will be extracted

    private AllCreatedProjects allCreatedProjects;




    public AccountExtractor(Account sourceAccount) {
        this.sourceAccount = sourceAccount;

        allCreatedProjects = AllCreatedProjects.getInstance();

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

        List<Project> allProjects = allCreatedProjects.getProjectsList();

        List<Project> extractedProjects = new ArrayList<>();

        for (String id : accountPartakingProjectIDs) {
            for (Project p : allProjects) {
                if (p.getProjectID().equals(id)) {
                    extractedProjects.add(p);
                }
            }
        }

        return extractedProjects;

    }

    /**
     *
     * @return a List of projects that the account has created
     * based on the project IDs stored
     * within the accounts partakingProjectIDs field
     */
    private List<Project> extractListOfCreatedProjectsFromAccount() {

        List<String> accountCreatedProjectIDs = sourceAccount.getAllProjectsPartaking();

        List<Project> allProjects = allCreatedProjects.getProjectsList();

        List<Project> extractedProjects = new ArrayList<>();

        for (String id : accountCreatedProjectIDs) {
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


}
