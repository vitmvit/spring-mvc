package by.vitikova.spring.mvc.model.dto.auth;

import by.vitikova.spring.mvc.model.dto.RoleDto;

import java.util.Set;

public record SignUpDto(
        String login,
        String password,
        String passwordConfirm,
        Set<RoleDto> roleList
) {
}