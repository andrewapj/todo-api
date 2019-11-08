package com.github.andrewapj.todoapi.infrastructure.repository;

import com.github.andrewapj.todoapi.domain.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository to perform persistence operations on {@link Todo}.
 */
@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
}
