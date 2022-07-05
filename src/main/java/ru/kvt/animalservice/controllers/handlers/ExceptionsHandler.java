package ru.kvt.animalservice.controllers.handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.kvt.animalservice.dto.errors.ErrorDto;
import ru.kvt.animalservice.dto.errors.ErrorMessage;
import ru.kvt.animalservice.exceptions.*;

@ControllerAdvice
@Slf4j
public class ExceptionsHandler {

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorDto> handleException(Exception exception) {
        log.error(exception.getMessage());

        if (exception instanceof MissingServletRequestParameterException)
            return process(HttpStatus.BAD_REQUEST, ErrorMessage.INVALID_REQUEST_PARAMS);

        if (exception instanceof MethodArgumentNotValidException)
            return process(HttpStatus.BAD_REQUEST, ErrorMessage.INVALID_REQUEST_PARAMS);

        if (exception instanceof MethodArgumentTypeMismatchException)
            return process(HttpStatus.BAD_REQUEST, ErrorMessage.INVALID_REQUEST_PARAMS);

        if (exception instanceof HttpMessageNotReadableException)
            return process(HttpStatus.BAD_REQUEST, ErrorMessage.INVALID_REQUEST_PARAMS);

        if (exception instanceof BadCredentialsException)
            return process(HttpStatus.UNAUTHORIZED, ErrorMessage.INCORRECT_CREDENTIALS);

        if (exception instanceof AccessDeniedException)
            return process(HttpStatus.UNAUTHORIZED, ErrorMessage.USER_UNAUTHORIZED);

        if (exception instanceof UsernameNotFoundException)
            return process(HttpStatus.NOT_FOUND, ErrorMessage.USER_NOT_FOUND);

        if (exception instanceof UserAlreadyExistsException)
            return process(HttpStatus.BAD_REQUEST, ErrorMessage.USER_ALREADY_EXISTS);

        if (exception instanceof UserNotFoundException)
            return process(HttpStatus.NOT_FOUND, ErrorMessage.USER_NOT_FOUND);

        if (exception instanceof AnimalNotFoundException)
            return process(HttpStatus.NOT_FOUND, ErrorMessage.ANIMAL_NOT_FOUND);

        if (exception instanceof SpeciesNotFoundException)
            return process(HttpStatus.NOT_FOUND, ErrorMessage.SPECIES_NOT_FOUND);

        if (exception instanceof AttemptsLimitExceededException)
            return process(HttpStatus.FORBIDDEN, ErrorMessage.ATTEMPTS_LIMIT_EXCEEDED);

        if (exception instanceof CurrentUsersAnimalNotFoundException)
            return process(HttpStatus.NOT_FOUND, ErrorMessage.CURRENT_USER_ANIMAL_NOT_FOUND);

        return process(HttpStatus.INTERNAL_SERVER_ERROR, ErrorMessage.UNEXPECTED_ERROR);
    }

    private ResponseEntity<ErrorDto> process(HttpStatus httpStatus, ErrorMessage errorMessage) {
        ErrorDto err = new ErrorDto(httpStatus.value(), httpStatus.getReasonPhrase(), errorMessage.getMessage());
        return new ResponseEntity<>(err, httpStatus);
    }

}
