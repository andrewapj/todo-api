package com.github.andrewapj.todoapi.api.mapper;

import com.github.andrewapj.todoapi.api.model.ApiTodoList;
import com.github.andrewapj.todoapi.domain.TodoList;
import org.mapstruct.Mapper;

/**
 * Mapper to convert between {@link ApiTodoList} and {@link TodoList}.
 */
@Mapper(componentModel = "spring")
public interface TodoListMapper {

    ApiTodoList toApiObject(TodoList todoList);
}
