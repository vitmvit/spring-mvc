package by.vitikova.spring.mvc.config.filter;

import by.vitikova.spring.mvc.converter.UserConverter;
import by.vitikova.spring.mvc.exception.InvalidJwtException;
import by.vitikova.spring.mvc.model.entity.User;
import by.vitikova.spring.mvc.service.AuthService;
import by.vitikova.spring.mvc.service.UserService;
import by.vitikova.spring.mvc.util.TokenUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@AllArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private AuthService authClient;
    private UserService userService;
    private UserConverter userConverter;
    private TokenUtil tokenUtil;

    /**
     * Метод, выполняющий перехват запросов и проверку токена
     *
     * @param request     объект HttpServletRequest, представляющий HTTP запрос
     * @param response    объект HttpServletResponse, представляющий HTTP ответ
     * @param filterChain объект FilterChain, представляющий цепочку фильтров
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        try {
            // Проверяем, является ли запрос запросом на Swagger UI
            if (request.getRequestURI().contains("/auth")) {
                // Если да, пропускаем фильтр и передаем запрос дальше
                filterChain.doFilter(request, response);
                return;
            }
            var token = this.recoverToken(request);
            var login = tokenUtil.getUsername(token);
            User user = userConverter.convert(userService.findByLogin(login));
            var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            if (Boolean.FALSE.equals(authClient.check(token))) {
                throw new InvalidJwtException("INVALID_TOKEN_ERROR");
            }
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            throw new InvalidJwtException("INVALID_TOKEN_ERROR");
        }
    }

    /**
     * Метод для извлечения токена из HTTP запроса
     *
     * @param request объект HttpServletRequest, представляющий HTTP запрос
     * @return строковое значение токена
     */
    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null)
            return null;
        return authHeader.replace("Bearer ", "");
    }
}
