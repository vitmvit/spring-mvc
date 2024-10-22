package by.vitikova.spring.mvc.converter;

import by.vitikova.spring.mvc.model.dto.RoleDto;
import by.vitikova.spring.mvc.model.dto.UserDto;
import by.vitikova.spring.mvc.model.dto.create.UserCreateDto;
import by.vitikova.spring.mvc.model.dto.update.UserUpdateDto;
import by.vitikova.spring.mvc.model.entity.Role;
import by.vitikova.spring.mvc.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.Set;

/**
 * Конвертер для преобразования между объектами пользовательского интерфейса и сущностями.
 * <p>
 * Этот интерфейс определяет методы для преобразования объектов типа {@link User},
 * {@link UserDto}, {@link UserCreateDto} и {@link UserUpdateDto}.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleConverter {

    /**
     * Преобразует объект типа {@link User} в объект типа {@link UserDto}.
     *
     * @param source исходный объект типа User.
     * @return преобразованный объект типа UserDto.
     */
    Set<Role> convert(Set<RoleDto> source);
}