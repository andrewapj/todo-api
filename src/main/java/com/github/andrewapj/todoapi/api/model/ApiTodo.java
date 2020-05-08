package com.github.andrewapj.todoapi.api.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.github.andrewapj.todoapi.domain.Todo;
import lombok.Builder;
import lombok.Value;

/**
 * Api bean representing a {@link Todo}.
 */
@Value
@Builder
@JsonDeserialize(builder = ApiTodo.ApiTodoBuilder.class)
public final class ApiTodo {

    private final Long id;
    private final String text;
    private final boolean completed;

    /**
     * Builder for the class.
     */
    @JsonPOJOBuilder(withPrefix = "")
    public static final class ApiTodoBuilder {

    }
}
