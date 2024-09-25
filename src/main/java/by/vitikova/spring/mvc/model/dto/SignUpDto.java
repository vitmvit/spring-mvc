package by.vitikova.spring.mvc.model.dto;

import by.vitikova.spring.mvc.model.RoleName;

public record SignUpDto(
        String login,
        String passwordHash,
        RoleName role) {
}
