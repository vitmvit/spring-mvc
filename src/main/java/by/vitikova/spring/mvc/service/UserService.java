package by.vitikova.spring.mvc.service;

import by.vitikova.spring.mvc.model.dto.UserCreateDto;
import by.vitikova.spring.mvc.model.dto.UserDto;
import by.vitikova.spring.mvc.model.dto.UserUpdateDto;

import java.util.List;

public interface UserService {

    UserDto findById(Long id);

    boolean existsByLogin(String login);

    UserDto findByLogin(String login);

    List<UserDto> findAll();

    UserDto create(UserCreateDto dto);

    UserDto update(UserUpdateDto dto);

    void deleteById(Long id);
}
