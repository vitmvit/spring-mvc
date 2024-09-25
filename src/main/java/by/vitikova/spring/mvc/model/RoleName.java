package by.vitikova.spring.mvc.model;

import lombok.Getter;

import java.util.stream.Stream;

@Getter
public enum RoleName {
    ADMIN("ADMIN"),
    SUPPORT("SUPPORT"),
    VET("VET"),
    EDITOR("EDITOR"),
    USER("USER");

    private final String role;

    RoleName(String role) {
        this.role = role;
    }

    public static RoleName getRoleName(String roleString) {
        return Stream.of(RoleName.values())
                .filter(role -> roleString.equals(role.getRole()))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
