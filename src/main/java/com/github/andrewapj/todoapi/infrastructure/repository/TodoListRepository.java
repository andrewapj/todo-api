package com.github.andrewapj.todoapi.infrastructure.repository;

import com.github.andrewapj.todoapi.domain.TodoList;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository to perform persistence operations on {@link TodoList}.
 */
@Repository
public interface TodoListRepository extends JpaRepository<TodoList, Long> {

    @Query("SELECT t FROM TodoList t JOIN FETCH t.items td")
    Optional<TodoList> findByIdWithTodos(long todoListId);
}
