package com.github.andrewapj.todoapi.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.github.andrewapj.todoapi.domain.TodoList;
import com.github.andrewapj.todoapi.domain.exception.ErrorType;
import com.github.andrewapj.todoapi.domain.exception.PersistenceException;
import com.github.andrewapj.todoapi.factory.ClockFactory;
import com.github.andrewapj.todoapi.infrastructure.repository.TodoListRepository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TodoListPersistenceServiceImplTest {

    @Autowired private TodoListPersistenceServiceImpl service;
    @PersistenceContext private EntityManager entityManager;

    @Test
    public void shouldCreateNew() {

        TodoList savedTodoList = service.create();

        assertThat(savedTodoList.getId()).isNotZero();
        assertThat(entityManager.find(TodoList.class, savedTodoList.getId())).isNotNull();
    }

    @Test
    public void should_ThrowException_WhenUnableToPersistCreate() {

        // Given: The repository will throw an exception
        TodoListRepository repository = mock(TodoListRepository.class);
        when(repository.save(any(TodoList.class)))
            .thenThrow(new DataIntegrityViolationException("Exception"));

        // When: We call the service
        // Then: It should throw the correct exception
        try {
            TodoListPersistenceServiceImpl service = new TodoListPersistenceServiceImpl(repository,
                ClockFactory.getDefaultFixedClock());
            service.create();
            fail("Expected a Persistence Exception");
        } catch (PersistenceException ex) {
            assertThat(ex.getErrorType()).isEqualTo(ErrorType.CREATE_TODOLIST);
            assertThat(ex.getObjectId()).isNull();
            assertThat(ex.getTimestamp())
                .isEqualTo(String.valueOf(ClockFactory.getDefaultFixedClock().millis()));
        }
    }
}
