package sample;

public class DuplicateUsernameException extends Throwable {

    public DuplicateUsernameException(String message) {
        super(message);
    }
}
