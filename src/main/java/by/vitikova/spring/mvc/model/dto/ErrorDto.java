package by.vitikova.spring.mvc.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO для представления ошибок.
 * <p>
 * Содержит сообщение об ошибке и код ошибки.
 */
@Getter
@Setter
@AllArgsConstructor
public class ErrorDto {

    private final String errorMessage;
    private final Integer errorCode;
}