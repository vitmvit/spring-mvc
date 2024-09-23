package by.vitikova.spring.mvc.controller.advice;

import by.vitikova.spring.mvc.exception.EntityNotFoundException;
import by.vitikova.spring.mvc.exception.OperationException;
import by.vitikova.spring.mvc.model.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Контроллер для обработки исключений в приложении.
 * <p>
 * Этот класс перехватывает исключения, возникающие в контроллерах, и
 * возвращает клиенту соответствующие сообщения об ошибках в формате {@link ErrorDto}.
 */
@RestControllerAdvice
public class ExceptionHandlerController {

    /**
     * Обработчик исключений для случаев, когда запрашиваемый ресурс не найден.
     *
     * @param e исключение {@link EntityNotFoundException}, содержащее информацию о причине ошибки.
     * @return объект {@link ErrorDto} с сообщением об ошибке и статусом 404 (Not Found).
     */
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto error(EntityNotFoundException e) {
        return new ErrorDto(e.getMessage(), HttpStatus.NOT_FOUND.value());
    }

    /**
     * Обработчик исключений для случаев, когда операция не может быть выполнена
     * из-за некорректных данных.
     *
     * @param e исключение {@link OperationException}, содержащее информацию о причине ошибки.
     * @return объект {@link ErrorDto} с сообщением об ошибке и статусом 400 (Bad Request).
     */
    @ExceptionHandler(OperationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto error(OperationException e) {
        return new ErrorDto(e.getMessage(), HttpStatus.BAD_REQUEST.value());
    }

    /**
     * Общий обработчик исключений для всех остальных случаев.
     *
     * @param e общее исключение {@link Exception}, которое может произойти в приложении.
     * @return объект {@link ErrorDto} с сообщением об ошибке и статусом 500 (Internal Server Error).
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDto error(Exception e) {
        return new ErrorDto(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}