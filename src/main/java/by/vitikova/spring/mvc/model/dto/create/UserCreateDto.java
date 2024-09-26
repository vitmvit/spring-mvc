package by.vitikova.spring.mvc.model.dto.create;

import by.vitikova.spring.mvc.constant.RoleName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateDto {

    private String login;

    private String passwordHash;
    private String passwordHashConfirm;
    private RoleName role;
}