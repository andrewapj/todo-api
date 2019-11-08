package com.github.andrewapj.todoapi.service.impl;

import com.github.andrewapj.todoapi.domain.Todo;
import com.github.andrewapj.todoapi.domain.TodoList;
import com.github.andrewapj.todoapi.domain.exception.ErrorType;
import com.github.andrewapj.todoapi.domain.exception.PersistenceException;
import com.github.andrewapj.todoapi.infrastructure.repository.TodoListRepository;
import com.github.andrewapj.todoapi.service.TodoPersistenceService;
import java.time.Clock;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of a {@link TodoPersistenceService}.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class TodoPersistenceServiceImpl implements TodoPersistenceService {

    private final TodoListRepository todoListRepository;
    private final Clock clock;

    @Override
    public Todo create(Long todoListId, Todo todo) {

        TodoList todoList = todoListRepository.findById(todoListId)
            .orElseThrow(() ->
                PersistenceException.builder()
                    .errorType(ErrorType.TODOLIST_NOTFOUND)
                    .objectId(null)
                    .timestamp(String.valueOf(clock.millis()))
                    .build());

        todoList.addItem(todo);
        return todo;
    }
}
