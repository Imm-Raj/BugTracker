package sample;

public class Positions {
    // a constant array that holds all valid position words
    private static final String validPositions[] = {
            "Junior Developer", "Developer", "Analyst", "Tester",
    };

    // a constant array that holds all valid admin position words
    private static final String validAdministratorPositions[] = {
            "Team Leader", "Senior Developer", "CEO", "Project Manager"
    };

    /**
     * Constructor for positions
     */
    public Positions()
    {
    }

    public  String[] getValidPositions() {
        return validPositions;
    }

    public String[] getValidAdministratorPositions() {
        return validAdministratorPositions;
    }

    /**
     * Check whether a given String is a valid position word.
     * @param aString The string to be checked.
     * @return true if it is valid, false if it isn't.
     */
    public boolean isPosition(String aString)
    {
        if(aString != null){
            for(int i = 0; i < validPositions.length; i++) {
                if(validPositions[i].equals(aString))
                    return true;
            }
        }
        // if we get here, the string was not found in the positions
        return false;
    }

    /**
     * Check whether a given String is a valid  admin position word.
     * @param aString The string to be checked.
     * @return true if it is valid, false if it isn't.
     */
    public boolean isAdminPosition(String aString)
    {
        if(aString != null){
            for(int i = 0; i < validAdministratorPositions.length; i++) {
                if(validAdministratorPositions[i].equals(aString))
                    return true;
            }
        }
        // if we get here, the string was not found in the positions
        return false;
    }

    /**
     * Print all valid positions to System.out.
     */
    public void showAllPositions()
    {
        for(String position : validPositions) {
            System.out.print(position + "  ");
        }
        System.out.println();
    }

    /**
     * Print all valid positions to System.out.
     */
    public void showAllAdminPositions()
    {
        for(String position : validPositions) {
            System.out.print(position + "  ");
        }
        System.out.println();
    }
}
