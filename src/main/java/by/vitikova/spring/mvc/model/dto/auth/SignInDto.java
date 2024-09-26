package by.vitikova.spring.mvc.model.dto.auth;

public record SignInDto(
        String login,
        String passwordHash) {
}