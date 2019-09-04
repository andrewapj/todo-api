package com.github.andrewapj.todoapi.infrastructure;

import com.github.andrewapj.todoapi.domain.TodoList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository to perform persistence operations on {@link TodoList}.
 */
@Repository
public interface TodoListRepository extends JpaRepository<TodoList, Long> {
}
