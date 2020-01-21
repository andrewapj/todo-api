package com.github.andrewapj.todoapi.api.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@JsonDeserialize(builder = ApiTodoListSummary.ApiTodoListSummaryBuilder.class)
public class ApiTodoListSummary {

    private final Long id;
    private final Long todosCount;

    @JsonPOJOBuilder(withPrefix = "")
    public static final class ApiTodoListSummaryBuilder {
    }
}
