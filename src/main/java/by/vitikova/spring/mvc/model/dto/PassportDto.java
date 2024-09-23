package by.vitikova.spring.mvc.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO для представления паспортных данных.
 * <p>
 * Содержит серию и номер паспорта пользователя.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PassportDto {

    private String passportSeries;
    private String passportNumber;
}