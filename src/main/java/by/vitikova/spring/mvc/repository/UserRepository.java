package by.vitikova.spring.mvc.repository;

import by.vitikova.spring.mvc.model.entity.User;

import java.util.List;

public interface UserRepository {

    User findById(Long id);

    List<User> findAll();

    User create(User user);

    User update(User user);

    void deleteById(Long id);
}