package com.github.andrewapj.todoapi.api.controller;

import com.github.andrewapj.todoapi.api.mapper.TodoMapper;
import com.github.andrewapj.todoapi.api.model.ApiTodo;
import com.github.andrewapj.todoapi.domain.Todo;
import com.github.andrewapj.todoapi.domain.TodoList;
import com.github.andrewapj.todoapi.service.TodoPersistenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller that defines endpoints for operations on {@link Todo}.
 */
@RestController
@RequiredArgsConstructor
public class TodoController {

    private final TodoPersistenceService todoPersistenceService;
    private final TodoMapper mapper;

    /**
     * Create a new {@link Todo} and attach it to the relevant {@link TodoList}.
     * @param todoListId        the id of the {@link TodoList}.
     * @param apiTodo           the api representation of the {@link Todo} to be saved.
     * @return                  the saved data.
     */
    @PostMapping(value = "/todolists/{todoListId}/todos",
        consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ApiTodo> createAndAdd(final @PathVariable Long todoListId,
                                         final @RequestBody ApiTodo apiTodo) {

        Todo todo = todoPersistenceService.create(todoListId, mapper.toDomainObject(apiTodo));

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(mapper.toApiObject(todo));
    }

    /**
     * Updates an existing {@link Todo}.
     *
     * @param todoListId        the todo list id that the todo is attached to.
     * @param todoId            the id of the todo item.
     * @param apiTodo           the data used for the update.
     * @return                  the updated item.
     */
    @PutMapping(value = "/todolists/{todoListId}/todos/{todoId}",
        consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ApiTodo> updateTodo(final @PathVariable Long todoListId,
                                       final @PathVariable Long todoId,
                                       final @RequestBody ApiTodo apiTodo) {
        Todo todo = todoPersistenceService.update(
            todoListId, todoId, mapper.toDomainObject(apiTodo));

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(mapper.toApiObject(todo));
    }
}
