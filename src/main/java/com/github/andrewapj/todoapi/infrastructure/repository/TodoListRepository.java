package com.github.andrewapj.todoapi.infrastructure.repository;

import com.github.andrewapj.todoapi.domain.TodoList;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository to perform persistence operations on {@link TodoList}.
 */
@Repository
public interface TodoListRepository extends JpaRepository<TodoList, Long> {

    /**
     * Finds a todo list by id and fetches the todo items within it also.
     *
     * @param todoListId the todo list id.
     * @return an optional containing the todo list.
     */
    @Query("SELECT t FROM TodoList t LEFT JOIN FETCH t.items td WHERE t.id = :todoListId")
    Optional<TodoList> findByIdWithTodos(@Param("todoListId") long todoListId);
}
