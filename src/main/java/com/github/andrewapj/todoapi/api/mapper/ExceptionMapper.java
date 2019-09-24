package com.github.andrewapj.todoapi.api.mapper;

import com.github.andrewapj.todoapi.api.model.ApiError;
import com.github.andrewapj.todoapi.domain.exception.PersistenceException;
import org.mapstruct.Mapper;

/**
 * Maps between exception types and API Error types.
 */
@Mapper(componentModel = "spring")
public interface ExceptionMapper {

    ApiError toApiObject(PersistenceException exception);
}
