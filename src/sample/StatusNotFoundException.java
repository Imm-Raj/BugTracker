package sample;

public class StatusNotFoundException extends Exception {
    public StatusNotFoundException(String message) {
        super(message + " is not a valid status value");
    }
}
