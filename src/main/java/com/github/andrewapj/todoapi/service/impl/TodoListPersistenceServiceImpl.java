package com.github.andrewapj.todoapi.service.impl;

import com.github.andrewapj.todoapi.domain.Todo;
import com.github.andrewapj.todoapi.domain.TodoList;
import com.github.andrewapj.todoapi.domain.exception.ErrorType;
import com.github.andrewapj.todoapi.domain.exception.NotFoundException;
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

    @Override
    public void delete(final long todoListId) {

        TodoList todoList = repository.findById(todoListId)
            .orElseThrow(() -> buildNotFoundException(ErrorType.TODOLIST_NOTFOUND, todoListId));

        repository.delete(todoList);
    }

    @Override
    public TodoList deleteTodo(final long todoListId, final long todoId) {

        TodoList foundTodoList = repository.findById(todoListId)
            .orElseThrow(() -> buildNotFoundException(ErrorType.TODOLIST_NOTFOUND, todoListId));

        Todo foundTodo = foundTodoList.getItems().stream()
            .filter(todo -> todo.getId().equals(todoId))
            .findFirst()
            .orElseThrow(() -> buildNotFoundException(ErrorType.TODO_NOTFOUND, todoId));

        foundTodoList.getItems().remove(foundTodo);
        return foundTodoList;
    }

    private NotFoundException buildNotFoundException(final ErrorType errorType,
                                                     final long objectId) {
        return NotFoundException.builder()
            .errorType(errorType)
            .objectId(String.valueOf(objectId))
            .timestamp(String.valueOf(clock.millis()))
            .build();
    }
}
