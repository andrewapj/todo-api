package com.github.andrewapj.todoapi.api.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

/**
 * Api bean representing a TodoList.
 */
@Value
@Builder
@JsonDeserialize(builder = ApiTodoList.ApiTodoListBuilder.class)
public final class ApiTodoList {

    private final Long id;

    /**
     * Builder for this bean that also maintains compatibility with Jackson.
     */
    @JsonPOJOBuilder(withPrefix = "")
    public static final class ApiTodoListBuilder {
    }
}
