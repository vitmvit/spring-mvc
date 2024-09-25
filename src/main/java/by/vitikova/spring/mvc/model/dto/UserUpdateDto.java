package by.vitikova.spring.mvc.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateDto {

    private Long id;
    private String login;
    private String passwordHash;
}
