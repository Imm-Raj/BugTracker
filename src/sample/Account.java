package sample;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Iterator;

public class Account {

    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private int age;
    private LocalDate DOB;
    private LocalDate dateCreated;
    private LocalDate dateClosed;
    private LocalDate revisedDate;

    private boolean isOpen;

    private String[] allPositions;

    private Positions positionsList;

    private ArrayList<String> allProjectsPartaking; //A list of the IDs of projects the account is partaking in

    private ArrayList<String> allTicketsPartaking; //A list of the IDs of the tickets the account is partaking in

    private ArrayList<String> allProjectsCreated; //A list of IDs of the projects that the account has created

    private  ArrayList<String> allTicketsCreated; //A list of IDs of the tickets that the account has created


    public Account(String userName, String password, String firstName, String lastName, int yearB, int monthB, int dayB, String[] enteredPositions)
            throws PositionNotFoundException, NullPointerException {

        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;

        if (password != null) {
            this.password = password;
        } else {
            throw new NullPointerException("Pass word for account cannot be null");
        }

        age = calculateAge(yearB, monthB, dayB);
        positionsList = new Positions();

        DOB = LocalDate.of(yearB, monthB, dayB);


        validatePositions(enteredPositions);


        allPositions = enteredPositions;


        allProjectsPartaking = new ArrayList<>();

        allTicketsPartaking = new ArrayList<>();


        allProjectsCreated = new ArrayList<>();

        allTicketsCreated = new ArrayList<>();


    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public LocalDate getDOB() {
        return DOB;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public LocalDate getRevisedDate() {
        return revisedDate;
    }

    public String[] getAllPositions() {
        return allPositions;
    }

    public Positions getPositionsList() {
        return positionsList;
    }

    public ArrayList<String> getAllProjectsPartaking() {
        return allProjectsPartaking;
    }

    public ArrayList<String> getAllTicketsPartaking() {
        return allTicketsPartaking;
    }

    public ArrayList<String> getAllProjectsCreated() {
        return allProjectsCreated;
    }

    public ArrayList<String> getAllTicketsCreated() {
        return allTicketsCreated;
    }

    /**
     * calculates the age of the account creator
     * @param year
     * @param month
     * @param day
     * @return age - the age of the account creator
     */
    private int calculateAge(int year, int month, int day) {

        LocalDate DOB = LocalDate.of(year, month, day);
        dateCreated = LocalDate.now();
        revisedDate = getDateCreated();

        age = Period.between(DOB,revisedDate).getYears();
        return age;

    }

    /**
     * A method that validates all normal positions entered by the account creator
     * @param positions
     * @throws PositionNotFoundException
     * @throws NullPointerException
     */
    private void validatePositions(String[] positions) throws PositionNotFoundException, NullPointerException {

        if (positions == null) {
            throw new NullPointerException("No positions entered for validation can be null");
        }

        for (String p : positions) {
            if (!positionsList.isPosition(p)) {

                throw new PositionNotFoundException("There is no such position " + p);

            }
        }

    }

    /**
     * A method that updates all positions the account has at the company
     * @param newCurrentPositions all Positions the employee now has
     * @throws PositionNotFoundException
     */
    public void updatePosition(String[] newCurrentPositions)
            throws PositionNotFoundException {

        validatePositions(newCurrentPositions);

        allPositions = newCurrentPositions;


    }

    /**
     * Adds a project ID to the list of created project IDs
     * @param enteredProjectCreatedID
     */
    public void addProjectIDToAllCreatedProjectsID(String enteredProjectCreatedID) {
        allProjectsCreated.add(enteredProjectCreatedID);
    }

    /**
     * Adds a ticket ID to the list of created ticket IDs
     * @param enteredTicketCreatedID
     */
    public void addTicketIDtoAllCreatedTicketsID(String enteredTicketCreatedID) {
        allTicketsCreated.add(enteredTicketCreatedID);
    }

    /**
     * Adds a project ID to the list of partaking project IDs
     * @param enteredProjectID
     */
    public void addProjectIDtoPartakingProjects(String enteredProjectID) {
        allProjectsPartaking.add(enteredProjectID);
    }

    /**
     * Adds a ticket ID to the list of partaking ticket IDs
     * @param enteredTicketID
     */
    public void addTicketIDtoPartakingTickets(String enteredTicketID) {
        allTicketsPartaking.add(enteredTicketID);
    }


    /**
     * Removes a selected ticketID string from the list of partaking ticketID strings
     * uses the ticket id to remove parameter
     * throws Illegal argument exception if the ticket is not found
     * @param ticketIDToRemove
     */
    public void removePartakingTicketFromAccount(String ticketIDToRemove) throws IllegalArgumentException {

        Iterator it = allTicketsPartaking.iterator();

        boolean correctID = false;

        while (it.hasNext()) {
            String tickString = (String) it.next();

            if (tickString.equals(ticketIDToRemove)) {
                it.remove();
                correctID = true;
            }
        }

        if (!correctID) {
            throw new IllegalArgumentException("ID of ticket to remove " + ticketIDToRemove + " is not there");
        }
    }

    /**
     * Removes a selected projectID string from the list of partaking projectID strings
     * uses the project id of project to remove
     * throws Illegal argument exception if the project is not found
     * @param projectIDToRemove
     */
    public void removePartakingProjectFromAccount(String projectIDToRemove) throws IllegalArgumentException {

        Iterator it = allProjectsPartaking.iterator();

        boolean correctID = false;

        while (it.hasNext()) {
            String projString = (String) it.next();

            if (projString.equals(projectIDToRemove)) {
                it.remove();
                correctID = true;
            }
        }


        if (!correctID) {
            throw new IllegalArgumentException("ID of project to remove " + projectIDToRemove + " is not there");
        }

    }


    public String listerString(ArrayList<String> list) {
        String outputString = "";


            for (String s : list) {

                outputString += ", " + s;
            }

        return outputString;
    }



    /**
     * An auxiliary method used for testing - provides a
     * String representation of the account object
     */
    public void accountToString() {
        System.out.println();
        System.out.println("UserName: " + userName);
        System.out.println("Name: " + firstName + "  " + lastName);
        System.out.println("Created Projects " + listerString(allProjectsCreated));
        System.out.println("Created Tickets " + listerString(allTicketsCreated));
        System.out.println("All Projects partaking: " + listerString(allProjectsPartaking));
        System.out.println("All Tickets partaking: " + listerString(allTicketsPartaking));
    }

}









