package com.github.andrewapj.todoapi.api.advice;

import com.github.andrewapj.todoapi.api.mapper.ExceptionMapper;
import com.github.andrewapj.todoapi.api.model.ApiError;
import com.github.andrewapj.todoapi.domain.exception.ErrorType;
import com.github.andrewapj.todoapi.domain.exception.NotFoundException;
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
     * Handles exceptions where a required resource was not found.
     *
     * @param ex the exception to handle.
     * @return the api error response.
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError> handleException(final NotFoundException ex) {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(mapper.toApiObject(ex));
    }

    /**
     * Handles all other exceptions thrown in the application.
     *
     * @param ex the exception to handle.
     * @return the api error response.
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiError> handleException(final RuntimeException ex) {

        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ApiError.builder()
                .errorType(ErrorType.INTERNAL_SERVER_ERROR)
                .objectId(null)
                .timestamp(String.valueOf(clock.millis()))
                .build());
    }
}
