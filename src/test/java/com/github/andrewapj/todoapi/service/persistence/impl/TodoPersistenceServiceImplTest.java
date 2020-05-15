package com.github.andrewapj.todoapi.service.persistence.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hibernate.testing.transaction.TransactionUtil.doInJPA;
import static org.junit.Assert.fail;

import com.github.andrewapj.todoapi.domain.Todo;
import com.github.andrewapj.todoapi.domain.TodoList;
import com.github.andrewapj.todoapi.domain.exception.ErrorType;
import com.github.andrewapj.todoapi.domain.exception.NotFoundException;
import com.github.andrewapj.todoapi.factory.TestDataFactory;
import java.time.Clock;
import javax.persistence.EntityManagerFactory;
import lombok.Getter;
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

    @Autowired
    private TodoPersistenceServiceImpl service;
    @Autowired
    @Qualifier("fixed")
    private Clock fixedClock;
    @Autowired
    @Getter
    private EntityManagerFactory entityManagerFactory;

    /*
     * Create new todo
     */

    @Test
    @Sql({"classpath:sql/truncate.sql", "classpath:sql/empty_todolist_only.sql"})
    public void should_AddTodo() {

        // When: We add a new todo to an existing list
        Todo returnedTodo = service.create(1L, TestDataFactory.getDefaultTodo(null));

        // Then: The TodoList should contain the todo item
        doInJPA(this::getEntityManagerFactory, em -> {
            Todo foundTodo = em.find(TodoList.class, 1L).getItems().get(0);
            assertThat(foundTodo.getId()).isNotZero();
            assertThat(foundTodo)
                .isEqualToIgnoringGivenFields(TestDataFactory.getDefaultTodo(null), "id");
        });

        // And: The returned todo should be correct
        assertThat(returnedTodo.getId()).isNotZero();
        assertThat(returnedTodo)
            .isEqualToIgnoringGivenFields(TestDataFactory.getDefaultTodo(null), "id");
    }

    @Test
    @Sql("classpath:sql/truncate.sql")
    public void should_ThrowErrorWhenAddingTodo_ToMissingTodoList() {

        try {
            service.create(1L, TestDataFactory.getDefaultTodo(null));
            fail("Should have thrown an exception");
        } catch (NotFoundException ex) {
            assertThat(ex.getErrorType()).isEqualTo(ErrorType.TODOLIST_NOTFOUND);
            assertThat(ex.getObjectId()).isEqualTo("1");
            assertThat(ex.getTimestamp()).isEqualTo(String.valueOf(fixedClock.millis()));
        }
    }

    /*
     * Update
     */

    @Test
    @Sql({"classpath:sql/truncate.sql", "classpath:/sql/single_todolist_with_single_todo.sql"})
    public void should_UpdateExistingTodo() {

        // When: The todo is updated.
        Todo todoToUpdate = doInJPA(this::getEntityManagerFactory, em -> {
            return em.find(Todo.class, 1L)
                .setText("Updated Text")
                .setCompleted(true);
        });
        Todo updatedTodo = service.update(1L, 1L, todoToUpdate);

        // Then: The updated todo should be returned.
        assertThat(updatedTodo).isEqualTo(todoToUpdate);

        // And: The todo in the DB should be correct.
        doInJPA(this::getEntityManagerFactory, em -> {
            assertThat(em.find(Todo.class, 1L)).isEqualTo(todoToUpdate);
        });
    }

    @Test(expected = NotFoundException.class)
    @Sql("classpath:sql/truncate.sql")
    public void should_ThrowNotFound_WhenMissingTodoListForUpdate() {

        Todo todo = TestDataFactory.getDefaultTodo(1L);
        service.update(1L, 1L, todo);
    }

    @Test(expected = NotFoundException.class)
    @Sql({"classpath:sql/truncate.sql", "classpath:sql/empty_todolist_only.sql"})
    public void should_ThrowNotFound_WhenMissingTodoForUpdate() {

        // When: A missing todo is updated.
        // Then: We should get a not found exception
        Todo todo = TestDataFactory.getDefaultTodo(1L);
        service.update(1L, 1L, todo);
    }
}
