package com.github.andrewapj.todoapi.feature;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.assertj.core.api.Assertions.assertThat;

import com.github.andrewapj.todoapi.api.controller.TodoListController;
import com.github.andrewapj.todoapi.api.model.ApiTodoList;
import com.github.andrewapj.todoapi.domain.TodoList;
import com.github.andrewapj.todoapi.feature.spec.RequestSpecification;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CreateTodoListTest {

    private static final String PATH = "todolists/";

    @Autowired private TodoListController controller;
    @PersistenceContext EntityManager entityManager;

    @Test
    public void should_CreateEmptyTodoList() {

        // When: A request to create a new TodoList is made
        MockMvcResponse response = given()
            .standaloneSetup(controller)
            .spec(RequestSpecification.SPEC)
            .post(PATH);

        // Then: The response should be correct
        ApiTodoList todoList = response
            .then()
            .statusCode(HttpStatus.CREATED.value())
            .extract().as(ApiTodoList.class);

        // And: The TodoList should be correct
        assertThat(todoList.getId()).isNotZero();

        // And: The DB should contain the TodoList
        assertThat(entityManager.find(TodoList.class, todoList.getId())).isNotNull();
    }
}
