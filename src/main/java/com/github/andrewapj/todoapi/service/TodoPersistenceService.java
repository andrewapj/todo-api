package com.github.andrewapj.todoapi.service;

import com.github.andrewapj.todoapi.domain.Todo;

/**
 * Responsible for persistence operations on {@link Todo}.
 */
public interface TodoPersistenceService {

    Todo create(Long todoListId, Todo todo);
}
