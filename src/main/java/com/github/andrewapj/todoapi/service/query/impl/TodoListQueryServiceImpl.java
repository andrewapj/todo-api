package com.github.andrewapj.todoapi.service.query.impl;

import com.github.andrewapj.todoapi.domain.TodoList;
import com.github.andrewapj.todoapi.domain.TodoListSummary;
import com.github.andrewapj.todoapi.infrastructure.repository.TodoListRepository;
import com.github.andrewapj.todoapi.service.query.TodoListQueryService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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
    public Optional<TodoList> findByIdWithTodos(final long todoListId) {
        return repository.findByIdWithTodos(todoListId);
    }

    @Override
    public List<TodoListSummary> findSummaries() {

        return repository.findAll().stream()
            .map(todoList -> TodoListSummary
                .builder()
                .id(todoList.getId())
                .todosCount(todoList.getItems().size())
                .build())
            .collect(Collectors.toList());
    }
}
