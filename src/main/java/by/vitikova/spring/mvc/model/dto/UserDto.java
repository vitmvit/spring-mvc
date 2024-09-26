package by.vitikova.spring.mvc.model.dto;

import by.vitikova.spring.mvc.constant.RoleName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

    private String id;
    private String login;
    private String passwordHash;
    private RoleName role;
}