package com.github.andrewapj.todoapi.service.persistence.impl;

import com.github.andrewapj.todoapi.domain.Todo;
import com.github.andrewapj.todoapi.domain.TodoList;
import com.github.andrewapj.todoapi.domain.exception.ErrorType;
import com.github.andrewapj.todoapi.domain.exception.NotFoundException;
import com.github.andrewapj.todoapi.infrastructure.repository.TodoListRepository;
import com.github.andrewapj.todoapi.service.persistence.TodoPersistenceService;
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
    public Todo create(final Long todoListId, final Todo todo) {

        TodoList todoList = todoListRepository.findById(todoListId)
            .orElseThrow(() ->
                NotFoundException.builder()
                    .errorType(ErrorType.TODOLIST_NOTFOUND)
                    .objectId(String.valueOf(todoListId))
                    .timestamp(String.valueOf(clock.millis()))
                    .build());

        todo.setId(null);
        todoList.addItem(todo);
        return todo;
    }

    @Override
    public Todo update(final Long todoListId, final Long todoId, final Todo updatedTodo) {

        TodoList todoList = todoListRepository.findById(todoListId)
            .orElseThrow(() ->
                NotFoundException.builder()
                    .errorType(ErrorType.TODOLIST_NOTFOUND)
                    .objectId(String.valueOf(todoListId))
                    .timestamp(String.valueOf(clock.millis()))
                    .build());

        return todoList.getItems().stream()
            .filter(todo -> todo.getId().equals(todoId))
            .findFirst()
            .map(todo -> todo.merge(updatedTodo))
            .orElseThrow(() ->
                NotFoundException.builder()
                    .errorType(ErrorType.TODO_NOTFOUND)
                    .objectId(String.valueOf(todoId))
                    .timestamp(String.valueOf(clock.millis()))
                    .build());
    }
}
