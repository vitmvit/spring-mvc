package by.vitikova.spring.mvc.service.impl;

import by.vitikova.spring.mvc.config.TokenProvider;
import by.vitikova.spring.mvc.converter.RoleConverter;
import by.vitikova.spring.mvc.converter.UserConverter;
import by.vitikova.spring.mvc.exception.EntityNotFoundException;
import by.vitikova.spring.mvc.exception.InvalidJwtException;
import by.vitikova.spring.mvc.model.dto.auth.JwtDto;
import by.vitikova.spring.mvc.model.dto.auth.SignInDto;
import by.vitikova.spring.mvc.model.dto.auth.SignUpDto;
import by.vitikova.spring.mvc.model.entity.User;
import by.vitikova.spring.mvc.repository.UserRepository;
import by.vitikova.spring.mvc.service.AuthService;
import by.vitikova.spring.mvc.service.RoleService;
import by.vitikova.spring.mvc.service.UserService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Collectors;

import static by.vitikova.spring.mvc.constant.Constant.*;

/**
 * Реализация сервиса аутентификации
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final UserConverter userConverter;
    private final RoleConverter roleConverter;
    private final TokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @Value("${security.jwt.token.secret-key}")
    private String secretKey;

    /**
     * Метод для регистрации нового пользователя
     *
     * @param dto объект SignUpDto, содержащий данные для регистрации
     * @return объект JwtDto, содержащий JWT-токен
     */
    @Transactional
    public JwtDto signUp(SignUpDto dto) {
        if (!dto.password().equals(dto.passwordConfirm())) {
            throw new InvalidJwtException(PASSWORD_ERROR);
        }
        if (Boolean.TRUE.equals(userRepository.existsUserByLogin(dto.login()))) {
            throw new InvalidJwtException(USERNAME_IS_EXIST);
        }
        var encryptedPassword = passwordEncoder.encode(dto.password());
//        var roleSet = roleConverter.convert(dto.roleList());
        var roleSet = dto.roleList().stream()
                .map(roleDto -> roleService.findByName(roleDto.getName()))
                .collect(Collectors.toSet());
        var newUser = new User(dto.login(), encryptedPassword, roleSet);
        userRepository.save(newUser);
        return buildJwt(dto.login(), dto.password());
    }

    /**
     * Метод для аутентификации пользователя
     *
     * @param dto объект SignInDto, содержащий данные для аутентификации
     * @return объект JwtDto, содержащий JWT-токен
     */
    @Override
    public JwtDto signIn(SignInDto dto) {
        try {
            Optional<User> userOptional = userRepository.findByLogin(dto.login());
            if (userOptional.isEmpty()) {
                throw new InvalidJwtException(USERNAME_NOT_EXIST);
            }
            if (passwordEncoder.matches(dto.password(), userOptional.get().getPasswordHash())) {
                return buildJwt(dto.login(), dto.password());
            }
            throw new InvalidJwtException("PIZDETC");
        } catch (Exception e) {
            throw new EntityNotFoundException(e.getMessage());
        }
    }

    /**
     * Проверка прав доступа пользователя по JWT токену.
     *
     * @param token JWT токен
     * @return true, если у пользователя есть доступ, иначе false
     */
    @Override
    public boolean check(String token) {
        return checkToken(token);
    }

    /**
     * Проверяет валидность JWT токена.
     *
     * @param token JWT токен
     * @return true, если токен валиден, иначе false
     */
    private boolean checkToken(String token) {
        try {
            var algorithm = Algorithm.HMAC256(secretKey);
            JWT.require(algorithm)
                    .build()
                    .verify(token)
                    .getSubject();
            return true;
        } catch (Exception ignored) {
        }
        return false;
    }

    /**
     * Метод для создания и возвращения JWT-токена
     *
     * @param login    логин пользователя
     * @param password пароль пользователя
     * @return объект JwtDto, содержащий JWT-токен
     */
    private JwtDto buildJwt(String login, String password) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(login, password);
        var authUser = authenticationManager.authenticate(usernamePassword);
        var accessToken = tokenProvider.generateAccessToken((User) authUser.getPrincipal());
        return new JwtDto(accessToken);
    }
}