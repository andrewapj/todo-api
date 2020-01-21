package com.github.andrewapj.todoapi.api.controller;

import com.github.andrewapj.todoapi.api.mapper.TodoListMapper;
import com.github.andrewapj.todoapi.api.model.ApiTodoList;
import com.github.andrewapj.todoapi.api.model.ApiTodoListSummary;
import com.github.andrewapj.todoapi.api.model.EmptyResponse;
import com.github.andrewapj.todoapi.domain.TodoList;
import com.github.andrewapj.todoapi.domain.exception.ErrorType;
import com.github.andrewapj.todoapi.domain.exception.NotFoundException;
import com.github.andrewapj.todoapi.service.persistence.TodoListPersistenceService;
import com.github.andrewapj.todoapi.service.query.TodoListQueryService;
import java.time.Clock;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller that defines endpoints for operations on {@link TodoList}.
 */
@RestController
@RequiredArgsConstructor
public class TodoListController {

    private final TodoListQueryService todoListQueryService;
    private final TodoListPersistenceService todoListPersistenceService;
    private final TodoListMapper mapper;
    private final Clock clock;

    @GetMapping(value = "/todolists/")
    public ResponseEntity<List<ApiTodoListSummary>> getSummaries() {

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(mapper.toApiObject(todoListQueryService.findSummaries()));
    }

    @GetMapping(value = "/todolists/{todoListId}")
    public ResponseEntity<ApiTodoList> getById(@PathVariable final Long todoListId) {

        return todoListQueryService.findByIdWithTodos(todoListId)
            .map(mapper::toApiObject)
            .map(apiTodoList ->
                ResponseEntity
                    .status(HttpStatus.OK)
                    .body(apiTodoList))
            .orElseThrow(() -> NotFoundException.builder()
                .errorType(ErrorType.TODOLIST_NOTFOUND)
                .objectId(String.valueOf(todoListId))
                .timestamp(String.valueOf(clock.millis()))
                .build());
    }

    /**
     * Create a new empty {@link TodoList}.
     * @return      the {@link ApiTodoList}.
     */
    @PostMapping(value = "/todolists")
    public ResponseEntity<ApiTodoList> create() {

        TodoList todoList = todoListPersistenceService.create();

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(mapper.toApiObject(todoList));
    }

    /**
     * Deletes a todo from the todo list.
     *
     * @param todoListId    the todo list id.
     * @param todoId        the todo to delete.
     * @return              the current todo list.
     */
    @DeleteMapping(value = "/todolists/{todoListId}/todos/{todoId}")
    ResponseEntity<ApiTodoList> deleteTodo(@PathVariable final Long todoListId,
                                           @PathVariable final Long todoId) {

        TodoList todoList = todoListPersistenceService.deleteTodo(todoListId, todoId);

        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body(mapper.toApiObject(todoList));
    }

    @DeleteMapping(value = "/todolists/{todoListId}")
    ResponseEntity<EmptyResponse> delete(@PathVariable final Long todoListId) {

        todoListPersistenceService.delete(todoListId);
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body(new EmptyResponse());
    }
}
