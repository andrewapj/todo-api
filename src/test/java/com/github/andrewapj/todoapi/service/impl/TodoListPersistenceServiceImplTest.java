package com.github.andrewapj.todoapi.service.impl;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.andrewapj.todoapi.domain.TodoList;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
    @PersistenceContext private EntityManager entityManager;

    @Test
    @Sql("classpath:sql/truncate.sql")
    public void shouldCreateNew() {

        TodoList savedTodoList = service.create();

        assertThat(savedTodoList.getId()).isNotZero();
        assertThat(entityManager.find(TodoList.class, savedTodoList.getId())).isNotNull();
    }
}
