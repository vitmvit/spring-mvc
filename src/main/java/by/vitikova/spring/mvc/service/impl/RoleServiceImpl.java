package by.vitikova.spring.mvc.service.impl;

import by.vitikova.spring.mvc.constant.RoleName;
import by.vitikova.spring.mvc.exception.EntityNotFoundException;
import by.vitikova.spring.mvc.model.entity.Role;
import by.vitikova.spring.mvc.repository.RoleRepository;
import by.vitikova.spring.mvc.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role findByName(RoleName name) {
        return roleRepository.findByName(name).orElseThrow(EntityNotFoundException::new);
    }
}