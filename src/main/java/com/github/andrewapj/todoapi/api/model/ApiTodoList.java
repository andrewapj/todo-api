package com.github.andrewapj.todoapi.api.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.github.andrewapj.todoapi.domain.TodoList;
import java.util.List;
import lombok.Builder;
import lombok.Value;

/**
 * Api bean representing a {@link TodoList}.
 */
@Value
@Builder
@JsonDeserialize(builder = ApiTodoList.ApiTodoListBuilder.class)
public final class ApiTodoList {

    private final Long id;
    private final List<ApiTodo> items;

    /**
     * Builder for this bean that also maintains compatibility with Jackson.
     */
    @JsonPOJOBuilder(withPrefix = "")
    public static final class ApiTodoListBuilder {
    }
}
