package com.github.andrewapj.todoapi.service.query.impl;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TodoListQueryServiceImplTest {

    @Autowired private TodoListQueryServiceImpl service;

    @Test
    @Sql({"classpath:sql/truncate.sql","classpath:/sql/single_todolist_with_two_todos.sql"})
    public void shouldGetTodo() {
        assertThat(service.findById(1L)).isNotEmpty();
    }

    @Test
    @Sql("classpath:sql/truncate.sql")
    public void shouldNotGetMissingTodo() {
        assertThat(service.findById(1L)).isEmpty();
    }
}
