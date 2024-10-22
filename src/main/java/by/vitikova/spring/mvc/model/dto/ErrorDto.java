package by.vitikova.spring.mvc.model.dto;

public record ErrorDto(
        String errorMessage,
        Integer errorCode) {
}