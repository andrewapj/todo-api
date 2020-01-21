package com.github.andrewapj.todoapi.api.mapper;

import com.github.andrewapj.todoapi.api.model.ApiTodoList;
import com.github.andrewapj.todoapi.api.model.ApiTodoListSummary;
import com.github.andrewapj.todoapi.domain.TodoList;
import com.github.andrewapj.todoapi.domain.TodoListSummary;
import java.util.List;
import org.mapstruct.Mapper;

/**
 * Mapper to convert between {@link ApiTodoList} and {@link TodoList},
 * or between {@link ApiTodoListSummary} and {@link TodoListSummary}.
 */
@Mapper(componentModel = "spring")
public interface TodoListMapper {

    ApiTodoList toApiObject(TodoList todoList);

    ApiTodoListSummary toApiObject(TodoListSummary summary);

    List<ApiTodoListSummary> toApiObject(List<TodoListSummary> summaries);
}
