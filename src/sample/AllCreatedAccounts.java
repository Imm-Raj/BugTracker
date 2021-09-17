package sample;

import java.util.ArrayList;
import java.util.List;

public class AllCreatedAccounts {

    // the instance stored within the singleton
    private static AllCreatedAccounts instance;
    // the instance stored within the singleton

    private ArrayList<Account> accountsList;

    private int normalAccNum;
    private int adminAccNum;

    private AllCreatedAccounts() {
        accountsList = new ArrayList<>();
        fillAccounts();

    }

    /**
     * Uses the singleton pattern to return the
     * same instance as initiated at first.
     * @return
     */
    public static AllCreatedAccounts getInstance() {
        if (instance == null) {
            instance =  new AllCreatedAccounts();
        }

        return instance;
    }

    public ArrayList<Account> getAccountsList() {
        return accountsList;
    }

    public int getNormalAccNum() {
        return normalAccNum;
    }

    /**
     * @return number of admin accounts
     */
    public int getAdminAccNum() {
        return adminAccNum;
    }


    public int getNumberOfAccounts() {
        return accountsList.size();
    }



    /**
     * Returns true if an account matches the userName and Password entered into this method
     * else return false
     * @param userName
     * @param passWord
     * @return boolean
     */
    public Account findAccountUsingLogInInfo(String userName, String passWord) throws AccountNotFoundException {

        Account resultAccount = null;
        boolean found = false;

        if (userName == null || passWord == null) {
            throw new NullPointerException("Find Account Login info must not be null");
        }

        System.out.println("Current account list  ");
        showAllAccountsText();

        for (Account a : accountsList) {
            if (a.getUserName().equals(userName) && a.getPassword().equals(passWord)) {
                resultAccount = a;
                found = true;
            }

        }

        if (found == false) {
            throw new AccountNotFoundException("There is no such account \n with these details");
        }

        return resultAccount;
    }

    /**
     * This method checks for accounts with same userNames or Password
     * Throws a related exception based on which field is violated which can
     * be caught by client classes and dealt with
     * @param enteredUserName
     * @param enteredPassword
     * @throws DuplicatePasswordException
     * @throws DuplicateUsernameException
     */
    public boolean checkForAccountsWithSameUserNameOrPassword(String enteredUserName, String enteredPassword)
            throws DuplicatePasswordException, DuplicateUsernameException {

        boolean result = true;
        for (Account ac : accountsList) {
            if (ac.getPassword().equals(enteredPassword)) {
                result = false;
                throw new DuplicatePasswordException("This password has already been \n  used please use another one");
            }

            if (ac.getUserName().equals(enteredUserName)) {
                result = false;
                throw new DuplicateUsernameException("This username has already been  \n used  please use another one");

            }
        }

        return result;
    }

    /**
     * This method takes in account information and uses it to create an account
     * and add it to the list.
     * The type of account created depends on whether the adminPositions param is null or not
     * therefore an if statement takes care of this
     * @param userName
     * @param password
     * @param firstName
     * @param lastName
     * @param yearB
     * @param monthB
     * @param dayB
     * @param enteredPositions
     * @param enteredAdminPositions
     * @throws PositionNotFoundException
     * @throws AdminPositionNotFoundException
     */
    public void addAccount(String userName, String password, String firstName, String lastName, int yearB, int monthB, int dayB, String[] enteredPositions, String[] enteredAdminPositions)
            throws PositionNotFoundException, AdminPositionNotFoundException, DuplicateUsernameException, DuplicatePasswordException {

      if (checkForAccountsWithSameUserNameOrPassword(userName, password)) {

          System.out.println("Entered positions length "  + enteredPositions.length);
          System.out.println("Entered Admin positions length " + enteredAdminPositions.length);

          if (enteredAdminPositions.length == 0) {
              // if there are no entered admin positions, create a normal account

              Account account = new Account(userName, password,firstName, lastName,yearB, monthB, dayB, enteredPositions);
              accountsList.add(account);
              normalAccNum++;

              assert normalAccNum + adminAccNum == accountsList.size();

          } else {
              // if there are entered admin positions, create an admin Account
              System.out.println("Creating admin account");
              Account account = new AdministratorAccount(userName, password,firstName, lastName,yearB, monthB, dayB, enteredPositions, enteredAdminPositions);
              accountsList.add(account);
              adminAccNum++;

              assert normalAccNum + adminAccNum == accountsList.size();
          }
      }

    }

    /**
     * An auxillary method that displays acounts ina text terminal
     */
    public void showAllAccountsText() {

        System.out.print("Accounts right now: ");
        for (Account a : accountsList) {

            System.out.print(a.getUserName() + " " + a.getAge());
            System.out.print("  ");

        }

        System.out.println();
    }

    public void fillAccounts() {

        String[] positions = {"Developer", "Analyst"};
        String[] adminPositions = {};

        try {
           addAccount("qwe1", "qwe", "Alan", "Henseworth", 1998, 7, 17, positions, adminPositions);
        } catch (PositionNotFoundException e) {
            e.printStackTrace();
        } catch (AdminPositionNotFoundException e) {
            e.printStackTrace();
        } catch (DuplicateUsernameException e) {
            e.printStackTrace();
        } catch (DuplicatePasswordException e) {
            e.printStackTrace();
        }

    }

    /**
     * Returns true if an account matches the userName entered into this method
     * else return false
     * @param userName
     * @return boolean
     */
    public Account findAccountUsingUserName(String userName) throws AccountNotFoundException, NullPointerException {

        Account resultAccount = null;
        boolean found = false;

        if (userName == null ) {
            throw new NullPointerException("Find Account Login info must not be null");
        }

        for (Account a : accountsList) {
            if (a.getUserName().equals(userName)) {
                resultAccount = a;
                found = true;
            }

        }

        if (found == false) {
            throw new AccountNotFoundException("There is no such account \n with this userName");
        }

        return resultAccount;
    }

    public List<Account> getListOfAccountsAccordingToEnteredListOfUserNames(List<String> listOfUserNames) throws AccountNotFoundException {
        List<Account> allAccounts = new ArrayList<>();

        for (String userName : listOfUserNames) {
            allAccounts.add(findAccountUsingUserName(userName));
        }

        return allAccounts;
    }




}
