package by.vitikova.spring.mvc.exception;

import static by.vitikova.spring.mvc.constant.Constant.ENTITY_NOT_FOUND_EXCEPTION;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException() {
        super(ENTITY_NOT_FOUND_EXCEPTION);
    }
}