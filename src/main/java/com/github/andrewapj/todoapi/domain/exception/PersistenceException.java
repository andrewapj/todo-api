package com.github.andrewapj.todoapi.domain.exception;

import lombok.Builder;
import lombok.Getter;

/**
 * Exception to represent a failure to perform a persistence operation within the application.
 */
@Getter
@Builder
public final class PersistenceException extends RuntimeException {

    private final ErrorType errorType;
    private final String objectId;
    private final String timestamp;
}
