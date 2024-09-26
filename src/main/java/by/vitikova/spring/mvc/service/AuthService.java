package by.vitikova.spring.mvc.service;

import by.vitikova.spring.mvc.model.dto.auth.JwtDto;
import by.vitikova.spring.mvc.model.dto.auth.SignInDto;
import by.vitikova.spring.mvc.model.dto.auth.SignUpDto;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface AuthService {

    JwtDto signUp(SignUpDto dto);

    JwtDto signIn(SignInDto dto);

    boolean check(String token) throws JsonProcessingException;
}