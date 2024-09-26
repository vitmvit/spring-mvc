package by.vitikova.spring.mvc.converter;

import by.vitikova.spring.mvc.model.dto.UserDto;
import by.vitikova.spring.mvc.model.dto.create.UserCreateDto;
import by.vitikova.spring.mvc.model.dto.update.UserUpdateDto;
import by.vitikova.spring.mvc.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserConverter {

    UserDto convert(User source);

    /**
     * Преобразование объекта UserDto в объект User
     *
     * @param source исходный комментарий типа User
     * @return преобразованный комментарий типа UserDto
     */
    User convert(UserDto source);

    /**
     * Преобразование объекта UserDto в объект UserCreateDto
     *
     * @param source исходный DTO для создания комментария типа UserCreateDto
     * @return преобразованный комментарий типа User
     */
    UserCreateDto convertToUser(User source);

    User convert(UserCreateDto source);

    /**
     * Объединяет данные из {@link UserUpdateDto} в существующую сущность {@link User}.
     *
     * @param user сущность {@link User}, которую нужно обновить.
     * @param dto  объект {@link UserUpdateDto}, содержащий данные для обновления.
     */
    User merge(@MappingTarget User user, UserUpdateDto dto);
}