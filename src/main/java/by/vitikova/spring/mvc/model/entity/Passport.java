package by.vitikova.spring.mvc.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

/**
 * Класс, представляющий паспортные данные.
 * <p>
 * Этот класс используется для встраивания паспортной информации в сущность пользователя.
 */
@Getter
@Setter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
public class Passport {

    @Column(name = "passport_series")
    private String passportSeries;

    @Column(name = "passport_number")
    private String passportNumber;
}