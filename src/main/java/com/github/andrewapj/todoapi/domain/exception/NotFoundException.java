package com.github.andrewapj.todoapi.domain.exception;

import java.util.Optional;
import lombok.Builder;
import lombok.Getter;

/**
 * Exception to represent that a required resource in the system was not found. This differs from
 * an empty {@link Optional} return in that the missing resource is required to complete an
 * operation within the system.
 */
@Getter
@Builder
public final class NotFoundException extends RuntimeException {

    private final ErrorType errorType;
    private final String objectId;
    private final String timestamp;
}
