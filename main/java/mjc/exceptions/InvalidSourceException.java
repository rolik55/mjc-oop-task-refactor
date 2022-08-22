package main.java.mjc.exceptions;

public class InvalidSourceException extends RuntimeException{
    public InvalidSourceException() {
    }

    public InvalidSourceException(String message) {
        super(message);
    }

    public InvalidSourceException(Throwable cause) {
        super(cause);
    }

}
