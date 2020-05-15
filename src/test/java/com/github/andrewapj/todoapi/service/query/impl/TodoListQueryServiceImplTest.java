package com.github.andrewapj.todoapi.service.query.impl;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.andrewapj.todoapi.domain.TodoList;
import com.github.andrewapj.todoapi.domain.TodoListSummary;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TodoListQueryServiceImplTest {

    @Autowired
    private TodoListQueryServiceImpl service;

    @Test
    @Sql({"classpath:sql/truncate.sql", "classpath:/sql/single_todolist_with_two_todos.sql"})
    public void shouldGetTodoList() {

        TodoList todoList = service.findByIdWithTodos(1L).orElseThrow();

        // Check that the todo items have been fetched (outside of a transactional scope)
        assertThat(todoList.getItems().size()).isEqualTo(2);
    }

    @Test
    @Sql({"classpath:sql/truncate.sql", "classpath:/sql/two_todolists_with_two_todos.sql"})
    public void shouldGetTodoListSummary() {

        List<TodoListSummary> summaries = service.findSummaries();

        assertThat(summaries.size()).isEqualTo(2);
        assertThat(summaries.get(0).getTodosCount()).isEqualTo(2);
        assertThat(summaries.get(1).getTodosCount()).isEqualTo(2);
    }

    @Test
    @Sql("classpath:sql/truncate.sql")
    public void shouldNotGetMissingTodo() {
        assertThat(service.findByIdWithTodos(1L)).isEmpty();
    }
}
