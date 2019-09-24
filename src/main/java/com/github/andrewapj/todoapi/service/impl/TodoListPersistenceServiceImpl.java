package com.github.andrewapj.todoapi.service.impl;

import com.github.andrewapj.todoapi.domain.TodoList;
import com.github.andrewapj.todoapi.domain.exception.ErrorType;
import com.github.andrewapj.todoapi.domain.exception.PersistenceException;
import com.github.andrewapj.todoapi.domain.factory.TodoListFactory;
import com.github.andrewapj.todoapi.infrastructure.repository.TodoListRepository;
import com.github.andrewapj.todoapi.service.TodoListPersistenceService;
import java.time.Clock;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of a {@link TodoListPersistenceService}.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class TodoListPersistenceServiceImpl implements TodoListPersistenceService {

    private final TodoListRepository repository;
    private final Clock clock;

    @Override
    public TodoList create() {

        try {
            return repository.save(TodoListFactory.buildNewTodoList(null));
        } catch (DataAccessException ex) {
            throw PersistenceException.builder()
                .errorType(ErrorType.CREATE_TODOLIST)
                .objectId(null)
                .timestamp(String.valueOf(clock.millis()))
                .build();
        }
    }
}
