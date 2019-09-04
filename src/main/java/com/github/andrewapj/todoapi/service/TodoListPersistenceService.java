package com.github.andrewapj.todoapi.service;

import com.github.andrewapj.todoapi.domain.TodoList;

/**
 * Responsible for persistence operations on {@link TodoList}.
 */
public interface TodoListPersistenceService {

    /**
     * Creates a new {@link TodoList}.
     *
     * @return  the new TodoList.
     */
    TodoList create();
}
