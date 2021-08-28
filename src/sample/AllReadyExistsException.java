package sample;

public class AllReadyExistsException extends Exception {
    public AllReadyExistsException(String s) {
        super(s + " already exists");
    }
}
