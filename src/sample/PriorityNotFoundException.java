package sample;

public class PriorityNotFoundException extends Exception {
    public PriorityNotFoundException(String message) {
        super(message + " is not a valid priority value");
    }
}
