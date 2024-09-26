package by.vitikova.spring.mvc.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Класс, представляющий пакет данных, содержащих полезную информацию о токене.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TokenPayload {

    private String username;
    private String role;

    /**
     * Время валидности токена
     */
    private Long ext;
}