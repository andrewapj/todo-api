package com.github.andrewapj.todoapi.api;

import com.github.andrewapj.todoapi.api.mapper.TodoListMapper;
import com.github.andrewapj.todoapi.api.model.ApiTodoList;
import com.github.andrewapj.todoapi.domain.TodoList;
import com.github.andrewapj.todoapi.service.TodoListPersistenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller that defines endpoints for operations on TodoLists.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/todolists")
public class TodoListController {

    private final TodoListPersistenceService todoListPersistenceService;
    private final TodoListMapper mapper;

    @PostMapping
    public ResponseEntity<ApiTodoList> create() {

        TodoList todoList = todoListPersistenceService.create();

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(mapper.toApiObject(todoList));
    }
}
