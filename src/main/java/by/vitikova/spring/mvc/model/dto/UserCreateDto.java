package by.vitikova.spring.mvc.model.dto;

import by.vitikova.spring.mvc.model.RoleName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateDto {

    //    @NotBlank
//    @Email
    private String login;

    private String passwordHash;
    private String passwordHashConfirm;
    private RoleName role;
}
