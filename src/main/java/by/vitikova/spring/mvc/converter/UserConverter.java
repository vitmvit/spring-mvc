package by.vitikova.spring.mvc.converter;

import by.vitikova.spring.mvc.model.dto.UserDto;
import by.vitikova.spring.mvc.model.dto.create.UserCreateDto;
import by.vitikova.spring.mvc.model.dto.update.UserUpdateDto;
import by.vitikova.spring.mvc.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

/**
 * Конвертер для преобразования между объектами пользовательского интерфейса и сущностями.
 * <p>
 * Этот интерфейс определяет методы для преобразования объектов типа {@link User},
 * {@link UserDto}, {@link UserCreateDto} и {@link UserUpdateDto}.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserConverter {

    /**
     * Преобразует объект типа {@link User} в объект типа {@link UserDto}.
     *
     * @param source исходный объект типа User.
     * @return преобразованный объект типа UserDto.
     */
    UserDto convert(User source);

    /**
     * Преобразует объект типа {@link User} в объект типа {@link UserCreateDto}.
     *
     * @param source исходный объект типа User, содержащий данные для создания.
     * @return преобразованный объект типа UserCreateDto.
     */
    UserCreateDto convertToUser(User source);

    /**
     * Преобразует объект типа {@link UserCreateDto} в объект типа {@link User}.
     *
     * @param source исходный DTO для создания пользователя.
     * @return преобразованный объект типа User.
     */
    User convert(UserCreateDto source);

    /**
     * Объединяет данные из {@link UserUpdateDto} в существующую сущность {@link User}.
     *
     * @param user сущность {@link User}, которую нужно обновить.
     * @param dto  объект {@link UserUpdateDto}, содержащий данные для обновления.
     * @return обновленная сущность User с данными из DTO.
     */
    User merge(@MappingTarget User user, UserUpdateDto dto);
}