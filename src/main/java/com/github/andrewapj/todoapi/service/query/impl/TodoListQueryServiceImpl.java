package com.github.andrewapj.todoapi.service.query.impl;

import com.github.andrewapj.todoapi.domain.TodoList;
import com.github.andrewapj.todoapi.infrastructure.repository.TodoListRepository;
import com.github.andrewapj.todoapi.service.query.TodoListQueryService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of a {@link TodoListQueryService}.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoListQueryServiceImpl implements TodoListQueryService {

    private final TodoListRepository repository;

    @Override
    public Optional<TodoList> findById(final long todoListId) {
        return repository.findById(todoListId);
    }
}
