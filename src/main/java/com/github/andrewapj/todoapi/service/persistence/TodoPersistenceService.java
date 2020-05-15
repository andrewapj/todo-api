package com.github.andrewapj.todoapi.service.persistence;

import com.github.andrewapj.todoapi.domain.Todo;

/**
 * Responsible for persistence operations on {@link Todo}.
 */
public interface TodoPersistenceService {

    /**
     * Creates a new {@link Todo}.
     *
     * @param todoListId the id of the todo list containing the todo.
     * @param todo       the todo to create.
     * @return the created todo.
     */
    Todo create(Long todoListId, Todo todo);

    /**
     * Updates an existing todo.
     *
     * @param todoListId the id of the todo list containing the todo.
     * @param todoId     the id of the todo to update
     * @param todo       the todo to update.
     * @return the updated todo.
     */
    Todo update(Long todoListId, Long todoId, Todo todo);
}
