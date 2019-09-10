package com.github.andrewapj.todoapi.domain.factory;

import com.github.andrewapj.todoapi.domain.TodoList;

/**
 * A factory to build different kinds of TodoLists.
 */
public class TodoListFactory {

    public static TodoList buildNewTodoList(final Long id) {
        return new TodoList()
            .setId(id);
    }
}
