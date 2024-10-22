package by.vitikova.spring.mvc.service;

import by.vitikova.spring.mvc.constant.RoleName;
import by.vitikova.spring.mvc.model.dto.RoleDto;
import by.vitikova.spring.mvc.model.entity.Role;

public interface RoleService {

    Role findByName(RoleName name);
}