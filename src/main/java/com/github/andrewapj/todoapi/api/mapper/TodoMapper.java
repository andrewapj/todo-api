package com.github.andrewapj.todoapi.api.mapper;

import com.github.andrewapj.todoapi.api.model.ApiTodo;
import com.github.andrewapj.todoapi.domain.Todo;
import org.mapstruct.Mapper;

/**
 * Mapper to convert between {@link ApiTodo} and {@link Todo}.
 */
@Mapper(componentModel = "spring")
public interface TodoMapper {

    ApiTodo toApiObject(Todo todo);

    Todo toDomainObject(ApiTodo todo);
}
