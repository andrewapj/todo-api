package com.github.andrewapj.todoapi.service;

import com.github.andrewapj.todoapi.domain.TodoList;
import com.github.andrewapj.todoapi.domain.exception.NotFoundException;

/**
 * Responsible for persistence operations on {@link TodoList}.
 */
public interface TodoListPersistenceService {

    /**
     * Creates a new {@link TodoList}.
     *
     * @return  the new TodoList.
     * @throws  NotFoundException when the {@link TodoList} could not be created.
     */
    TodoList create();
}
