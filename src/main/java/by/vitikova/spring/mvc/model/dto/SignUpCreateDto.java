package by.vitikova.spring.mvc.model.dto;

import by.vitikova.spring.mvc.model.RoleName;

public record SignUpCreateDto(
        String login,
        String password,
        String passwordConfirm,
        RoleName role) {
}
