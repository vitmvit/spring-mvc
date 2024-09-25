package by.vitikova.spring.mvc.model.dto;

public record SignInDto(
        String login,
        String passwordHash) {
}
