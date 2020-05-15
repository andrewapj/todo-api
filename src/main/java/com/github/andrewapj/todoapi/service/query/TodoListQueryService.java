package com.github.andrewapj.todoapi.service.query;

import com.github.andrewapj.todoapi.domain.TodoList;
import com.github.andrewapj.todoapi.domain.TodoListSummary;
import java.util.List;
import java.util.Optional;

/**
 * Responsible for querying a {@link TodoList}.
 */
public interface TodoListQueryService {

    /**
     * Finds a {@link TodoList} by its id. Fetches all the todo items within the list.
     *
     * @param todoListId the id of the todo list.
     * @return an optional containing the todo list.
     */
    Optional<TodoList> findByIdWithTodos(long todoListId);

    /**
     * Finds a list of todo list summaries.
     *
     * @return a list of todo list summaries.
     */
    List<TodoListSummary> findSummaries();
}
