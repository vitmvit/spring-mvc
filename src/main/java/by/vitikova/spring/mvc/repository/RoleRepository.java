package by.vitikova.spring.mvc.repository;

import by.vitikova.spring.mvc.constant.RoleName;
import by.vitikova.spring.mvc.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(RoleName name);
}