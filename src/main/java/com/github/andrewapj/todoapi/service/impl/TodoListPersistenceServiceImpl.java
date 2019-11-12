package com.github.andrewapj.todoapi.service.impl;

import com.github.andrewapj.todoapi.domain.TodoList;
import com.github.andrewapj.todoapi.infrastructure.repository.TodoListRepository;
import com.github.andrewapj.todoapi.service.TodoListPersistenceService;
import java.time.Clock;
import lombok.RequiredArgsConstructor;
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
        return repository.save(new TodoList().setId(null));
    }
}
