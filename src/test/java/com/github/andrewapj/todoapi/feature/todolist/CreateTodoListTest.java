package com.github.andrewapj.todoapi.feature.todolist;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.assertj.core.api.Assertions.assertThat;

import com.github.andrewapj.todoapi.api.controller.TodoListController;
import com.github.andrewapj.todoapi.api.model.ApiTodoList;
import com.github.andrewapj.todoapi.domain.TodoList;
import com.github.andrewapj.todoapi.feature.spec.RequestSpecification;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CreateTodoListTest {

    @PersistenceContext
    EntityManager entityManager;
    @Autowired
    private TodoListController controller;

    @Test
    @Sql("classpath:sql/truncate.sql")
    public void should_CreateEmptyTodoList() {

        // When: A request to create a new TodoList is made
        // Then: The response should be correct
        ApiTodoList todoList = given()
            .standaloneSetup(controller)
            .spec(RequestSpecification.SPEC)
            .post("todolists/")
            .then()
            .statusCode(HttpStatus.CREATED.value())
            .extract().as(ApiTodoList.class);

        // And: The TodoList should be correct
        assertThat(todoList.getId()).isNotZero();

        // And: The DB should contain the TodoList
        assertThat(entityManager.find(TodoList.class, todoList.getId())).isNotNull();
    }
}
