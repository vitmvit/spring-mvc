package by.vitikova.spring.mvc.converter;

import by.vitikova.spring.mvc.model.dto.UserDto;
import by.vitikova.spring.mvc.model.dto.create.UserCreateDto;
import by.vitikova.spring.mvc.model.dto.update.UserUpdateDto;
import by.vitikova.spring.mvc.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

/**
 * Конвертер для преобразования между сущностями {@link User} и
 * их отображениями (DTO) в приложении.
 * <p>
 * Этот интерфейс использует MapStruct для автоматического генерирования
 * кода преобразования. Он описывает методы для конвертации сущностей в
 * DTO и обратно, а также для объединения данных из DTO в существующую
 * сущность.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserConverter {

    /**
     * Преобразует сущность {@link User} в объект {@link UserDto}.
     *
     * @param source сущность {@link User}, которую нужно преобразовать.
     * @return объект {@link UserDto}, представляющий данные пользователя.
     */
    UserDto convert(User source);

    /**
     * Преобразует объект {@link UserCreateDto} в сущность {@link User}.
     *
     * @param source объект {@link UserCreateDto}, содержащий данные нового пользователя.
     * @return созданная сущность {@link User}.
     */
    User convert(UserCreateDto source);

    /**
     * Объединяет данные из {@link UserUpdateDto} в существующую сущность {@link User}.
     *
     * @param user сущность {@link User}, которую нужно обновить.
     * @param dto  объект {@link UserUpdateDto}, содержащий данные для обновления.
     */
    User merge(@MappingTarget User user, UserUpdateDto dto);
}