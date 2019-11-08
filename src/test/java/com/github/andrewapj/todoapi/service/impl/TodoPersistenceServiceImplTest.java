package com.github.andrewapj.todoapi.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hibernate.testing.transaction.TransactionUtil.doInJPA;
import static org.junit.Assert.fail;

import com.github.andrewapj.todoapi.domain.Todo;
import com.github.andrewapj.todoapi.domain.TodoList;
import com.github.andrewapj.todoapi.domain.exception.ErrorType;
import com.github.andrewapj.todoapi.domain.exception.PersistenceException;
import com.github.andrewapj.todoapi.factory.TestDataFactory;
import java.time.Clock;
import javax.persistence.EntityManagerFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TodoPersistenceServiceImplTest {

    @Autowired private TodoPersistenceServiceImpl service;
    @Autowired @Qualifier("fixed") private Clock fixedClock;
    @Autowired private EntityManagerFactory entityManagerFactory;

    public EntityManagerFactory entityManagerFactory() {
        return entityManagerFactory;
    }

    @Test
    @Sql("classpath:sql/truncate.sql")
    public void should_AddTodo() {

        // Given: An empty TodoList exists
        TodoList existingTodoList = doInJPA(this::entityManagerFactory, em -> {
            TodoList todoList = TestDataFactory.getEmptyTodoList(null);
            em.persist(todoList);
            return todoList;
        });

        // When: We add a new todo to that list
        Todo returnedTodo = service.create(existingTodoList.getId(),
            TestDataFactory.getDefaultTodo(null));

        // Then: The TodoList should contain the todo item
        doInJPA(this::entityManagerFactory, em -> {
            Todo foundTodo = em.find(TodoList.class, existingTodoList.getId()).getItems().get(0);
            assertThat(foundTodo.getId()).isNotZero();
            assertThat(foundTodo)
                .isEqualToIgnoringGivenFields(TestDataFactory.getDefaultTodo(null),"id");
        });

        // And: The returned todo should be correct
        assertThat(returnedTodo.getId()).isNotZero();
        assertThat(returnedTodo)
            .isEqualToIgnoringGivenFields(TestDataFactory.getDefaultTodo(null),"id");
    }

    @Test
    @Sql("classpath:sql/truncate.sql")
    public void should_ThrowErrorWhenAddingTodo_ToMissingTodoList() {

        try {
            service.create(1L, TestDataFactory.getDefaultTodo(null));
            fail("Should have thrown an exception");
        } catch (PersistenceException ex) {
            assertThat(ex.getErrorType()).isEqualTo(ErrorType.TODOLIST_NOTFOUND);
            assertThat(ex.getObjectId()).isEqualTo(null);
            assertThat(ex.getTimestamp()).isEqualTo(String.valueOf(fixedClock.millis()));
        }
    }
}
