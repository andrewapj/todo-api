package com.github.andrewapj.todoapi.api.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Api bean representing a TodoList.
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class ApiTodoList {

    private Long id;
}
