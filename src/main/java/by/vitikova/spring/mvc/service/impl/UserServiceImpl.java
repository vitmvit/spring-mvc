package by.vitikova.spring.mvc.service.impl;

import by.vitikova.spring.mvc.converter.UserConverter;
import by.vitikova.spring.mvc.exception.EntityNotFoundException;
import by.vitikova.spring.mvc.exception.InvalidJwtException;
import by.vitikova.spring.mvc.model.dto.UserDto;
import by.vitikova.spring.mvc.model.dto.create.UserCreateDto;
import by.vitikova.spring.mvc.model.dto.update.UserUpdateDto;
import by.vitikova.spring.mvc.repository.UserRepository;
import by.vitikova.spring.mvc.service.UserService;
import by.vitikova.spring.mvc.util.TokenUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static by.vitikova.spring.mvc.constant.Constant.USERNAME_IS_EXIST;

/**
 * Реализация сервиса управления пользователями.
 */
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final TokenUtil tokenUtil;

    /**
     * Находит текущего пользователя по токену авторизации.
     *
     * @param auth токен авторизации для получения текущего пользователя.
     * @return объект {@link UserDto} с данными текущего пользователя.
     * @throws EntityNotFoundException если пользователь не найден.
     */
    @Override
    public UserDto findCurrentUser(String auth) {
        return userConverter.convert(userRepository.findByLogin(tokenUtil.getUsername(auth)).orElseThrow(EntityNotFoundException::new));
    }

    /**
     * Находит пользователя по его идентификатору.
     *
     * @param id идентификатор пользователя.
     * @return объект {@link UserDto} с данными пользователя.
     * @throws EntityNotFoundException если пользователь не найден.
     */
    @Override
    public UserDto findById(Long id) {
        return userConverter.convert(userRepository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    /**
     * Проверяет, существует ли пользователь с заданным логином.
     *
     * @param login логин пользователя.
     * @return true, если пользователь существует, иначе false.
     */
    @Override
    public boolean existsByLogin(String login) {
        return userRepository.existsUserByLogin(login);
    }

    /**
     * Получает список всех пользователей.
     *
     * @return список объектов {@link UserDto} с данными пользователей.
     */
    @Override
    public List<UserDto> findAll() {
        var userList = userRepository.findAll();
        return userList.stream().map(userConverter::convert).collect(Collectors.toList());
    }

    /**
     * Создает нового пользователя.
     *
     * @param dto данные для создания нового пользователя.
     * @return объект {@link UserDto} с данными созданного пользователя.
     * @throws InvalidJwtException если пользователь с таким логином уже существует.
     */
    @Override
    public UserDto create(UserCreateDto dto) {
        if (Boolean.FALSE.equals(userRepository.existsUserByLogin(dto.getLogin()))) {
            return userConverter.convert(userRepository.save(userConverter.convert(dto)));
        }
        throw new InvalidJwtException(USERNAME_IS_EXIST);
    }

    /**
     * Обновляет информацию о существующем пользователе.
     *
     * @param dto объект {@link UserUpdateDto} с новыми данными пользователя.
     * @return объект {@link UserDto} с обновлёнными данными пользователя.
     * @throws EntityNotFoundException если пользователь не найден.
     */
    @Override
    public UserDto update(UserUpdateDto dto) {
        var user = userRepository.findById(dto.getId()).orElseThrow(EntityNotFoundException::new);
        return userConverter.convert(userRepository.save(userConverter.merge(user, dto)));
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