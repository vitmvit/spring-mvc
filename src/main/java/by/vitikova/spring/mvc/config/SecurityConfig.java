package by.vitikova.spring.mvc.config;

import by.vitikova.spring.mvc.config.service.UserDetailsServiceImpl;
import by.vitikova.spring.mvc.constant.RoleName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

/**
 * Конфигурация безопасности приложения.
 * <p>
 * Этот класс управляет настройками безопасности, включая правила доступа к
 * различным конечным точкам API, обработку аутентификации и настройку фильтров
 * безопасности.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

//    @Lazy
//    @Autowired
//    private  SecurityFilter securityFilter;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    /**
     * Настраивает элементы, которые будут игнорироваться системой безопасности.
     * <p>
     * В данном случае запросы к "/api/auth/" будут игнорироваться.
     *
     * @return объект {@link WebSecurityCustomizer} для настройки безопасности веб-приложения
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers(
                "/api/auth/**"
        );
    }

    /**
     * Создает {@link SecurityFilterChain} для настройки правил доступа к конечным точкам API.
     * <p>
     * Этот метод определяет политику создания сессии, правила доступа
     * и добавляет кастомный фильтр безопасности.
     *
     * @param httpSecurity объект {@link HttpSecurity} для настройки безопасности
     * @return настроенная цепочка фильтров безопасности
     * @throws Exception если возникают проблемы с настройкой безопасности
     */
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.csrf(AbstractHttpConfigurer::disable)
//                // Своего рода отключение CORS (разрешение запросов со всех доменов)
//                .cors(cors -> cors.configurationSource(request -> {
//                    var corsConfiguration = new CorsConfiguration();
//                    corsConfiguration.setAllowedOriginPatterns(List.of("*"));
//                    corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//                    corsConfiguration.setAllowedHeaders(List.of("*"));
//                    corsConfiguration.setAllowCredentials(true);
//                    return corsConfiguration;
//                }))
//                // Настройка доступа к конечным точкам
//                .authorizeHttpRequests(request -> request
//                        // Можно указать конкретный путь, * - 1 уровень вложенности, ** - любое количество уровней вложенности
//                        .requestMatchers("/api/auth/**").permitAll()
//                        .requestMatchers("/swagger-ui/**", "/swagger-resources/*", "/v3/api-docs/**").permitAll()
//                        .requestMatchers("/api/users/me").hasRole("USER")
//                        .requestMatchers("/api/users/**").hasRole("ADMIN")
//                        .anyRequest().authenticated())
//                .sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS))
//                .authenticationProvider(authenticationProvider());
////                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
//        return http.build();
//    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.GET, "/api/users/me").hasAnyRole(RoleName.ADMIN.name(), RoleName.USER.name())
                        .requestMatchers(HttpMethod.GET, "/api/users/**").hasRole(RoleName.ADMIN.name())
                        .requestMatchers(HttpMethod.POST, "/api/users/**").hasRole(RoleName.ADMIN.name())
                        .requestMatchers(HttpMethod.PUT, "/api/users/**").hasRole(RoleName.ADMIN.name())
                        .requestMatchers(HttpMethod.DELETE, "/api/users/**").hasRole(RoleName.ADMIN.name())
                        .anyRequest().authenticated())
                .authenticationProvider(authenticationProvider())
//                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

//    /**
//     * Создает менеджер аутентификации.
//     * <p>
//     * Этот метод возвращает {@link AuthenticationManager}, который используется
//     * для выполнения аутентификации пользователей.
//     *
//     * @param authenticationConfiguration объект {@link AuthenticationConfiguration} для настройки аутентификации
//     * @return экземпляр {@link AuthenticationManager}
//     * @throws Exception если возникают проблемы с созданием менеджера аутентификации
//     */
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }

//    /**
//     * Создание менеджера аутентификации.
//     *
//     * @param userDetailsService сервис пользователей
//     * @param passwordEncoder    кодировщик пароля
//     * @return менеджер аутентификации
//     */
//    @Bean
//    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
//        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
//        authenticationProvider.setUserDetailsService(userDetailsService);
//        authenticationProvider.setPasswordEncoder(passwordEncoder);
//        return new ProviderManager(authenticationProvider);
//    }
//
//
    /**
     * Создание кодировщика пароля.
     *
     * @return кодировщик пароля
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}