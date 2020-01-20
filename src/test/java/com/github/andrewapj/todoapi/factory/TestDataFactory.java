package com.github.andrewapj.todoapi.factory;

import com.github.andrewapj.todoapi.api.model.ApiTodo;
import com.github.andrewapj.todoapi.domain.Todo;

public class TestDataFactory {

    public static Todo getDefaultTodo(final Long id) {
        return new Todo()
            .setId(id)
            .setText("A new Todo")
            .setCompleted(false);
    }

    public static ApiTodo getDefaultApiTodo(final Long id) {
        return ApiTodo.builder()
            .id(id)
            .text("A new Todo")
            .completed(false)
            .build();
    }
}
