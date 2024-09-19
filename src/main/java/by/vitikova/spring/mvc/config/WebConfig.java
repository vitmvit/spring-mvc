package by.vitikova.spring.mvc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Конфигурация веб-уровня приложения Spring.
 * <p>
 * Этот класс реализует интерфейс {@link WebMvcConfigurer}, который позволяет
 * настраивать компоненты Spring MVC, такие как настройки обработки запросов,
 * конфигурация ресурсов, интерсепторы и обработчики.
 */
@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
}