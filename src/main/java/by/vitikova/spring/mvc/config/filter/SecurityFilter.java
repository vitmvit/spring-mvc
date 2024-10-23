package by.vitikova.spring.mvc.config.filter;

import by.vitikova.spring.mvc.config.service.UserDetailsServiceImpl;
import by.vitikova.spring.mvc.exception.InvalidJwtException;
import by.vitikova.spring.mvc.repository.TokenRepository;
import by.vitikova.spring.mvc.service.AuthService;
import by.vitikova.spring.mvc.util.TokenUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import static by.vitikova.spring.mvc.constant.Constant.*;

/**
 * Фильтр для проверки безопасности запросов с использованием JWT (JSON Web Tokens).
 * Этот фильтр перехватывает входящие HTTP запросы, извлекает токен из заголовка
 * Authorization, валидирует его и устанавливает аутентификацию в контекст безопасности.
 */
@Component
@AllArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private AuthService authService;
    private TokenUtil tokenUtil;
    private TokenRepository tokenRepository;
    private UserDetailsServiceImpl userDetailsService;

    /**
     * Метод, выполняющий перехват запросов и проверку токена.
     *
     * @param request     объект HttpServletRequest, представляющий HTTP запрос
     * @param response    объект HttpServletResponse, представляющий HTTP ответ
     * @param filterChain объект FilterChain, представляющий цепочку фильтров
     * @throws InvalidJwtException если токен недействителен
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        try {
            // Проверяем, является ли запрос запросом на /auth
            if (request.getRequestURI().contains("/auth")) {
                // Если да, пропускаем фильтр и передаем запрос дальше
                filterChain.doFilter(request, response);
                return;
            }
            var token = this.recoverToken(request);
            var login = tokenUtil.getUsername(token);
            var user = userDetailsService.loadUserByUsername(login);
            var authentication = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            if (tokenRepository.existsByUsername(login)) {  // Лежит ли токен пользователя в черном списке
                throw new InvalidJwtException(NEED_LOGIN_ERROR);
            }
            if (Boolean.FALSE.equals(authService.check(token))) {
                throw new InvalidJwtException(INVALID_TOKEN_ERROR);
            }
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            throw new InvalidJwtException(INVALID_TOKEN_ERROR);
        }
    }

    /**
     * Метод для извлечения токена из HTTP запроса.
     *
     * @param request объект HttpServletRequest, представляющий HTTP запрос
     * @return строковое значение токена или {@code null}, если токен отсутствует
     */
    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader(AUTHORIZATION_HEADER);
        return authHeader == null ? null : authHeader.replace(BEARER_PREFIX, "");
    }
}