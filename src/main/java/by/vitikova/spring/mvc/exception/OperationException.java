package by.vitikova.spring.mvc.exception;

public class OperationException extends RuntimeException {

    public OperationException(String message) {
        super(message);
    }
}