package sample;

import javafx.geometry.Pos;

public class AdministratorAccount extends Account {

    private String[] allAdministrationPositions;

    private Positions positionsList;


    public AdministratorAccount(String userName, String password, String firstName, String lastName, int yearB, int monthB, int dayB, String[] enteredPositions, String[] enteredAdminPositions)
            throws PositionNotFoundException, AdminPositionNotFoundException, NullPointerException {

        super(userName, password,  firstName, lastName, yearB, monthB, dayB, enteredPositions);

        positionsList = new Positions();

        validateAdminPositions(enteredAdminPositions);

        allAdministrationPositions = enteredAdminPositions;
    }


    public String[] getAllAdministrationPositions() {
        return allAdministrationPositions;
    }

    /**
     * returns all admin and normal positions which the account
     * holds
     * @return String[] concatenated array
     */
    @Override
    public String[] getAllPositions() {
        return concatStringArrays(super.getAllPositions(), getAllAdministrationPositions());
    }


    /**
     * A method that validates all administration positions entered by the account creator
     *
     * @param enteredPositions
     * @throws AdminPositionNotFoundException
     */
    private void validateAdminPositions(String[] enteredPositions)
            throws AdminPositionNotFoundException, NullPointerException {

        if (enteredPositions == null) {
            throw new NullPointerException("No admin positions entered for validation can be null");
        }

        for (String p : enteredPositions) {
            if (!positionsList.isAdminPosition(p)) {

                throw new AdminPositionNotFoundException("There is no such admin position " + p);

            }
        }

    }

    /**
     * A method that updates all positions the account has at the company
     * @param newCurrentAdminPositions all Positions the employee now has
     * @throws PositionNotFoundException
     */
    public void updateAdminPosition(String[] newCurrentAdminPositions)
            throws PositionNotFoundException, AdminPositionNotFoundException {

        validateAdminPositions(newCurrentAdminPositions);

        allAdministrationPositions = newCurrentAdminPositions;


    }

    private  String[] concatStringArrays(String[] array1, String[] array2)
    {

        int aLen = array1.length;
        int bLen = array2.length;
        String[] result = new String[aLen + bLen];

        System.arraycopy(array1, 0, result, 0, aLen);
        System.arraycopy(array2, 0, result, aLen, bLen);


        return result;
    }


}


