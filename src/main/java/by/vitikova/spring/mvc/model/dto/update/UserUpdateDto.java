package by.vitikova.spring.mvc.model.dto.update;

import by.vitikova.spring.mvc.model.dto.create.UserCreateDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO для обновления информации о пользователе.
 * <p>
 * Является расширением {@link UserCreateDto} и добавляет поле для
 * идентификации пользователя (id).
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDto extends UserCreateDto {

    Long id;
}