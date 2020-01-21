package com.github.andrewapj.todoapi.service.persistence;

import com.github.andrewapj.todoapi.domain.Todo;
import com.github.andrewapj.todoapi.domain.TodoList;
import com.github.andrewapj.todoapi.domain.exception.NotFoundException;

/**
 * Responsible for persistence operations on {@link TodoList}.
 */
public interface TodoListPersistenceService {

    /**
     * Creates a new {@link TodoList}.
     *
     * @return  the new TodoList.
     * @throws  NotFoundException when the {@link TodoList} could not be created.
     */
    TodoList create();

    /**
     * Delete this todolist.
     *
     * @param todoListId    the todolist id.
     */
    void delete(long todoListId);

    /**
     * Delete a {@link Todo} from a {@link TodoList}.
     *
     * @param todoListId  the todolist to update.
     * @param todoId      the todo to remove.
     * @return            the updated todolist.
     */
    TodoList deleteTodo(long todoListId, long todoId);
}
