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

    /**
     * Maps a {@link TodoList} to an api object.
     *
     * @param todoList the todo list to map.
     * @return the api representation of a todo list.
     */
    ApiTodoList toApiObject(TodoList todoList);

    /**
     * Maps a {@link TodoListSummary} to an api object.
     *
     * @param summary the todo list summary to map.
     * @return the api representation of a todo list summary.
     */
    ApiTodoListSummary toApiObject(TodoListSummary summary);

    /**
     * Maps a List of {@link TodoListSummary} to api objects.
     *
     * @param summaries the list of todo list summaries to map.
     * @return the api representation of a list of todo list summaries.
     */
    List<ApiTodoListSummary> toApiObject(List<TodoListSummary> summaries);
}
