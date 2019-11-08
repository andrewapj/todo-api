package com.github.andrewapj.todoapi.api.advice;

import com.github.andrewapj.todoapi.api.mapper.ExceptionMapper;
import com.github.andrewapj.todoapi.api.model.ApiError;
import com.github.andrewapj.todoapi.domain.exception.ErrorType;
import com.github.andrewapj.todoapi.domain.exception.PersistenceException;
import java.time.Clock;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Controller Advice for the API controllers.
 */
@ControllerAdvice
@RequiredArgsConstructor
public class ApiControllerAdvice {

    private final ExceptionMapper mapper;
    private final Clock clock;

    /**
     * Handles {@link PersistenceException} for the API.
     *
     * @param ex        the exception to handle.
     * @return          the api error response.
     */
    @ExceptionHandler(PersistenceException.class)
    public ResponseEntity<ApiError> handleException(PersistenceException ex) {

        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(mapper.toApiObject(ex));
    }

    /**
     * Handles all other exceptions thrown in the application.
     *
     * @param ex        the exception to handle.
     * @return          the api error response.
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiError> handleException(RuntimeException ex) {

        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ApiError.builder()
                .errorType(ErrorType.INTERNAL_SERVER_ERROR)
                .objectId(null)
                .timestamp(String.valueOf(clock.millis()))
                .build());
    }
}
