package com.github.andrewapj.todoapi.api.mapper;

import com.github.andrewapj.todoapi.api.model.ApiTodo;
import com.github.andrewapj.todoapi.domain.Todo;
import org.mapstruct.Mapper;

/**
 * Mapper to convert between {@link ApiTodo} and {@link Todo}.
 */
@Mapper(componentModel = "spring")
public interface TodoMapper {

    /**
     * Maps from a {@link Todo} to an {@link ApiTodo}.
     *
     * @param todo the todo item to map.
     * @return the api representation of the todo.
     */
    ApiTodo toApiObject(Todo todo);

    /**
     * Maps from a {@link ApiTodo} to a {@link Todo}.
     *
     * @param todo the api todo item to convert.
     * @return the todo domain object.
     */
    Todo toDomainObject(ApiTodo todo);
}
