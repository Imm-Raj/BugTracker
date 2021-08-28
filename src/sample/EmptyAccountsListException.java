package sample;

public class EmptyAccountsListException extends Exception {
    public EmptyAccountsListException() {
        super("Account list is empty or is null");
    }
}
