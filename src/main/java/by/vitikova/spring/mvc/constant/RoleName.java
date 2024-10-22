package by.vitikova.spring.mvc.constant;

import lombok.Getter;

/**
 * Перечисление, представляющее роли пользователей в системе.
 * <p>
 * Это перечисление содержит доступные роли, используемые для управления
 * правами доступа к ресурсам приложения.
 */
@Getter
public enum RoleName {

    ADMIN,
    USER

//    ADMIN("ADMIN"),
//    USER("USER");
//
//    private final String role;
//
//    RoleName(String role) {
//        this.role = role;
//    }
}