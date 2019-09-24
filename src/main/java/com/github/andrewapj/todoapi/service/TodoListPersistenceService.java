package com.github.andrewapj.todoapi.service;

import com.github.andrewapj.todoapi.domain.TodoList;
import com.github.andrewapj.todoapi.domain.exception.PersistenceException;

/**
 * Responsible for persistence operations on {@link TodoList}.
 */
public interface TodoListPersistenceService {

    /**
     * Creates a new {@link TodoList}.
     *
     * @return  the new TodoList.
     * @throws  PersistenceException when the {@link TodoList} could not be created.
     */
    TodoList create();
}
