package by.vitikova.spring.mvc.config;

import by.vitikova.spring.mvc.model.entity.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static by.vitikova.spring.mvc.constant.Constant.GENERATION_TOKEN_ERROR;


/**
 * Класс, отвечающий за генерацию и проверку токенов аутентификации.
 */
@Component
public class TokenProvider {

    @Value("${security.jwt.token.secret-key}")
    private String jwtSecret;

    /**
     * Генерирует токен доступа для пользователя.
     *
     * @param user Пользователь, для которого генерируется токен доступа.
     * @return Сгенерированный токен доступа.
     * @throws JWTCreationException Если произошла ошибка при генерации токена.
     */
    public String generateAccessToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
            return JWT.create()
                    .withSubject(user.getUsername())
                    .withClaim("username", user.getUsername())
                    .withClaim("role", user.getRole().toString())
                    .withExpiresAt(genAccessExpirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new JWTCreationException(GENERATION_TOKEN_ERROR, exception);
        }
    }

    /**
     * Генерирует дату истечения срока действия токена доступа.
     *
     * @return Дата истечения срока действия токена доступа.
     */
    private Instant genAccessExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}