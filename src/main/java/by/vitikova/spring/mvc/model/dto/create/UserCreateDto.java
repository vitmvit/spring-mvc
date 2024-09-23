package by.vitikova.spring.mvc.model.dto.create;

import by.vitikova.spring.mvc.model.dto.PassportDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO для создания пользователя.
 * <p>
 * Содержит информацию о пользователе, необходимую для его создания.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateDto {

    private String name;
    private String surname;
    private PassportDto passport;
}