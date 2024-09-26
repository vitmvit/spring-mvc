package by.vitikova.spring.mvc.model.dto.auth;

import by.vitikova.spring.mvc.constant.RoleName;

public record SignUpDto(
        String login,
        String password,
        String passwordConfirm,
        RoleName role) {
}