package by.vitikova.spring.mvc;

public class InvalidJwtException extends RuntimeException{

    public InvalidJwtException(String message) {
        super(message);
    }
}
