package sample;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AllCreatedProjects {

    //Instance variable for singleton pattern
    private static  AllCreatedProjects instance;

    private List<Project> projectsList;

    private int currentProjectID; // An auxiliary id provider that increments each time a project is made
    // so that each project has a unique part id

    private AllCreatedProjects() {
        projectsList = new ArrayList<>();
        currentProjectID = 0;


    }

    /**
     * Returns an instance of AllCreatedProjects for the
     * singleton pattern
     * @return
     */
    public static AllCreatedProjects getInstance() {
        if (instance == null) {
            instance = new AllCreatedProjects();
        }
            return instance;

    }

    public List<Project> getProjectsList() {
        return projectsList;
    }

    public int getProjectsListSize() {
        return projectsList.size();
    }

    public int getCurrentProjectID() {
        return currentProjectID;
    }

    /**
     * A method that creates a project and adds a project to the projects list
     * Checks for any project with the same title if not AllReadyExistsException
     * is thrown
     * @param name
     * @param description
     * @param createdBy
     * @param enteredDevelopers
     * @throws AllReadyExistsException
     * @throws EmptyAccountsListException
     * @throws CreatorAccountNullException
     */
    public void addProject(String name, String description, Account createdBy, List<Account> enteredDevelopers)
            throws AllReadyExistsException, EmptyAccountsListException, CreatorAccountNullException {

        if (!checkForProjectsWithSameTitle(name)) {

            Project newProject = new Project(name, currentProjectID, description, createdBy, enteredDevelopers);
            projectsList.add(newProject);

            currentProjectID++;

        } else {
            throw new AllReadyExistsException(name);
        }


    }

    /**
     * A method that removes a selected project (Through project ID)
     * and removes it. Utilises an iterator and uses some casting
     * throws an exception if the project is not found
     * @param idOfProjectToRemove
     * @throws IllegalAccessException
     */
    public void removeProjectFromList(String idOfProjectToRemove) throws IllegalArgumentException {

        boolean correctID = false;
        Iterator it = projectsList.iterator();

        while (it.hasNext()) {
            Project project = (Project) it.next();
            if (project.getProjectID().equals(idOfProjectToRemove)) {
                project.close();
                it.remove();
                correctID = true;
            }
        }

        if (!correctID) {
            throw new IllegalArgumentException("The project with ID: " + idOfProjectToRemove + " is not there");
        }
    }

    /**
     * A method that selects a particular project from the list
     * based on project ID and returns the project
     * otherwise throws null pointer exception
     * @param idOfProjectToSelect
     * @return Project
     */
    public Project getProjectFromList(String idOfProjectToSelect) throws NullPointerException {


        Iterator it = projectsList.iterator();
        Project chosenProject = null;

        while (it.hasNext()) {
            Project project = (Project) it.next();
            if (project.getProjectID().equals(idOfProjectToSelect)) {
                chosenProject = project;
            }
        }

        if (chosenProject == null) {
            throw new NullPointerException("chosen project is not found");
        }

       return chosenProject;
    }

    /**
     * A method that checks for if a project exists with
     * the same title - this should not exist
     * @param enteredTitle
     * @return
     */
    private boolean checkForProjectsWithSameTitle(String enteredTitle) {

        boolean result = false;
        for (Project p : projectsList) {
            if (p.getName().equals(enteredTitle)) {
                result = true;
            }
        }

        return result;
    }

    public void printAllProjectsInfo() {
        for (Project p : projectsList) {
          p.printProjectInfo();

        }
    }

}
