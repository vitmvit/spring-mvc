package by.vitikova.spring.mvc.model.dto.create;

import by.vitikova.spring.mvc.model.entity.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserCreateDto {

    private String username;
    private String password;
    private String passwordConfirm;
    private Set<Role> roleList;
}