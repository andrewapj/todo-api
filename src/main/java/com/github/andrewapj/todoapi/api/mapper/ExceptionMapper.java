package com.github.andrewapj.todoapi.api.mapper;

import com.github.andrewapj.todoapi.api.model.ApiError;
import com.github.andrewapj.todoapi.domain.exception.NotFoundException;
import org.mapstruct.Mapper;

/**
 * Maps between exception types and API Error types.
 */
@Mapper(componentModel = "spring")
public interface ExceptionMapper {

    /**
     * Maps from a {@link NotFoundException} to an {@link ApiError} object.
     *
     * @param exception The exception to map into an api error.
     * @return The api representation of the exception.
     */
    ApiError toApiObject(NotFoundException exception);
}
