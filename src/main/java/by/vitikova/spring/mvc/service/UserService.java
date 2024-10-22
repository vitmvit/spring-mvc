package by.vitikova.spring.mvc.service;

import by.vitikova.spring.mvc.model.dto.UserDto;
import by.vitikova.spring.mvc.model.dto.create.UserCreateDto;
import by.vitikova.spring.mvc.model.dto.update.UserUpdateDto;

import java.util.List;

public interface UserService {

    UserDto findCurrentUser(String auth);

    UserDto findById(Long id);

    boolean existsByLogin(String login);

    List<UserDto> findAll();

    UserDto create(UserCreateDto dto);

    UserDto update(UserUpdateDto dto);

    void deleteById(Long id);
}