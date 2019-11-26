package com.github.andrewapj.todoapi.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.hibernate.testing.transaction.TransactionUtil.doInJPA;

import com.github.andrewapj.todoapi.domain.Todo;
import com.github.andrewapj.todoapi.domain.TodoList;
import com.github.andrewapj.todoapi.domain.exception.ErrorType;
import com.github.andrewapj.todoapi.domain.exception.NotFoundException;
import javax.persistence.EntityManagerFactory;
import lombok.Getter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TodoListPersistenceServiceImplTest {

    @Autowired private TodoListPersistenceServiceImpl service;
    @Autowired @Getter private EntityManagerFactory entityManagerFactory;

    @Test
    @Sql("classpath:sql/truncate.sql")
    public void shouldCreateNew() {

        TodoList savedTodoList = service.create();

        assertThat(savedTodoList.getId()).isNotZero();

        doInJPA(this::getEntityManagerFactory, em -> {
            assertThat(em.find(TodoList.class, savedTodoList.getId())).isNotNull();
        });
    }

    @Test
    @Sql({"classpath:sql/truncate.sql","classpath:/sql/single_todolist_with_two_todos.sql"})
    public void shouldDeleteExistingTodo() {

        TodoList todoList = service.deleteTodo(1L, 2L);

        assertThat(todoList.getItems().size()).isEqualTo(1);
        doInJPA(this::getEntityManagerFactory, em -> {
            assertThat(em.find(TodoList.class, 1L).getItems().size()).isEqualTo(1);
            assertThat(em.find(Todo.class, 2L)).isNull();
        });
    }

    @Test
    @Sql({"classpath:sql/truncate.sql","classpath:sql/empty_todolist_only.sql"})
    public void shouldNotDeleteMissingTodo() {

        try {
            service.deleteTodo(1L,999L);
            fail("Should get a not found exception - todo not found");
        } catch (NotFoundException ex) {
            assertThat(ex.getErrorType()).isEqualTo(ErrorType.TODO_NOTFOUND);
            assertThat(ex.getObjectId()).isEqualTo("999");
        }
    }

    @Test
    @Sql({"classpath:sql/truncate.sql"})
    public void shouldNotDeleteTodo_WithMissingTodoList() {

        try {
            service.deleteTodo(999L,1L);
            fail("Should get a not found exception - todo list not found");
        } catch (NotFoundException ex) {
            assertThat(ex.getErrorType()).isEqualTo(ErrorType.TODOLIST_NOTFOUND);
            assertThat(ex.getObjectId()).isEqualTo("999");
        }
    }
}
