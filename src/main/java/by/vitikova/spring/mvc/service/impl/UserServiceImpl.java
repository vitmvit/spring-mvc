package by.vitikova.spring.mvc.service.impl;

import by.vitikova.spring.mvc.converter.UserConverter;
import by.vitikova.spring.mvc.model.dto.UserDto;
import by.vitikova.spring.mvc.model.dto.create.UserCreateDto;
import by.vitikova.spring.mvc.model.dto.update.UserUpdateDto;
import by.vitikova.spring.mvc.model.entity.User;
import by.vitikova.spring.mvc.repository.UserRepository;
import by.vitikova.spring.mvc.service.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Реализация сервиса для управления пользователями.
 */
@Service
@Transactional
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;

    /**
     * Находит пользователя по его идентификатору и возвращает его представление.
     *
     * @param id идентификатор пользователя.
     * @return объект {@link UserDto} с информацией о пользователе.
     */
    @Override
    public UserDto findById(Long id) {
        return userConverter.convert(userRepository.findById(id));
    }

    /**
     * Находит всех пользователей и возвращает их представления.
     *
     * @return список объектов {@link UserDto} с информацией о всех пользователях.
     */
    @Override
    public List<UserDto> findAll() {
        List<User> userList = userRepository.findAll();
        return userList.stream().map(userConverter::convert).collect(Collectors.toList());
    }

    /**
     * Создает нового пользователя на основе переданных данных.
     *
     * @param dto объект {@link UserCreateDto} с данными для создания пользователя.
     * @return объект {@link UserDto} с информацией о созданном пользователе.
     */
    @Override
    public UserDto create(UserCreateDto dto) {
        return userConverter.convert(userRepository.create(userConverter.convert(dto)));
    }

    /**
     * Обновляет информацию о существующем пользователе.
     *
     * @param dto объект {@link UserUpdateDto} с обновленными данными пользователя.
     * @return объект {@link UserDto} с информацией об обновленном пользователе.
     */
    @Override
    public UserDto update(UserUpdateDto dto) {
        User user = userRepository.findById(dto.getId());
        return userConverter.convert(userRepository.update(userConverter.merge(user, dto)));
    }

    /**
     * Удаляет пользователя по его идентификатору.
     *
     * @param id идентификатор пользователя, которого нужно удалить.
     */
    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}