package by.vitikova.spring.mvc.model.entity;

import by.vitikova.spring.mvc.constant.RoleName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

/**
 * Класс, представляющий пакет данных, содержащих полезную информацию о токене.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TokenPayload {

    private String username;
    private Set<RoleName> role;

    /**
     * Время валидности токена
     */
    private Long ext;
}