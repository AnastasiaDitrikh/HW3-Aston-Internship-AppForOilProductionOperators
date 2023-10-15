package exceptions;

public class ConnectionDoesNotExistException extends RuntimeException{
    public ConnectionDoesNotExistException(String message) {
        super(message);
    }
}