package by.vitikova.spring.mvc.model.dto.create;

import by.vitikova.spring.mvc.model.entity.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserCreateDto {

    private String login;
    private String passwordHash;
    private String passwordHashConfirm;
    private Set<Role> roleList;
}