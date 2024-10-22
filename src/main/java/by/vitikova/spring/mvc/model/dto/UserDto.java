package by.vitikova.spring.mvc.model.dto;

import by.vitikova.spring.mvc.model.entity.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserDto {

    private String id;
    private String login;
    private String passwordHash;
    private Set<RoleDto> roleList;
}