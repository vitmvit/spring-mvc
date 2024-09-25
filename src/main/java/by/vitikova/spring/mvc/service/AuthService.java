package by.vitikova.spring.mvc.service;

import by.vitikova.spring.mvc.model.dto.JwtDto;
import by.vitikova.spring.mvc.model.dto.SignInDto;
import by.vitikova.spring.mvc.model.dto.SignUpCreateDto;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface AuthService {

    JwtDto signUp(SignUpCreateDto dto);

    JwtDto signIn(SignInDto dto);

    boolean check(String token) throws JsonProcessingException;
}