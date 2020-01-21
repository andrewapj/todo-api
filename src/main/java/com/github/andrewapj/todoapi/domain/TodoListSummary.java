package com.github.andrewapj.todoapi.domain;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class TodoListSummary {

    private final Long id;
    private final int todosCount;
}
