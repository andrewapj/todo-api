package com.github.andrewapj.todoapi.service.query;

import com.github.andrewapj.todoapi.domain.TodoList;
import java.util.Optional;

/**
 * Responsible for querying a {@link TodoList}.
 */
public interface TodoListQueryService {

    /**
     * Finds a {@link TodoList} by its id.
     *
     * @param todoListId    the is of the todo list.
     * @return              an optional containing the todo list.
     */
    Optional<TodoList> findById(long todoListId);
}
