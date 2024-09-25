package by.vitikova.spring.mvc.service;

import by.vitikova.spring.mvc.EntityNotFoundException;
import by.vitikova.spring.mvc.InvalidJwtException;
import by.vitikova.spring.mvc.converter.UserConverter;
import by.vitikova.spring.mvc.model.dto.UserCreateDto;
import by.vitikova.spring.mvc.model.dto.UserDto;
import by.vitikova.spring.mvc.model.dto.UserUpdateDto;
import by.vitikova.spring.mvc.model.entity.User;
import by.vitikova.spring.mvc.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static by.vitikova.spring.mvc.constant.Constant.USERNAME_IS_EXIST;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;

    @Override
    public UserDto findById(Long id) {
        return userConverter.convert(userRepository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public boolean existsByLogin(String login) {
        boolean user = userRepository.existsUserByLogin(login);
        return user;
    }

    @Override
    public UserDto findByLogin(String login) {
        return userConverter.convert(userRepository.findByLogin(login).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public List<UserDto> findAll() {
        List<User> userList = userRepository.findAll();
        return userList.stream().map(userConverter::convert).collect(Collectors.toList());
    }

    @Override
    public UserDto create(UserCreateDto dto) {
        if (Boolean.FALSE.equals(userRepository.existsUserByLogin(dto.getLogin()))) {
            var user = userConverter.convert(dto);
            return userConverter.convert(userRepository.save(user));
        }
        throw new InvalidJwtException(USERNAME_IS_EXIST);
    }

    @Override
    public UserDto update(UserUpdateDto dto) {
        User user = userRepository.findById(dto.getId()).orElseThrow(EntityNotFoundException::new);
        return userConverter.convert(userRepository.save(userConverter.merge(user, dto)));
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}