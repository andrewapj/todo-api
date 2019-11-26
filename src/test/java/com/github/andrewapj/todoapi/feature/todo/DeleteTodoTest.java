package com.github.andrewapj.todoapi.feature.todo;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hibernate.testing.transaction.TransactionUtil.doInJPA;

import com.github.andrewapj.todoapi.api.advice.ApiControllerAdvice;
import com.github.andrewapj.todoapi.api.controller.TodoListController;
import com.github.andrewapj.todoapi.api.model.ApiTodoList;
import com.github.andrewapj.todoapi.domain.TodoList;
import com.github.andrewapj.todoapi.feature.spec.RequestSpecification;
import javax.persistence.EntityManagerFactory;
import lombok.Getter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DeleteTodoTest {

    @Autowired private TodoListController controller;
    @Autowired private ApiControllerAdvice apiControllerAdvice;
    @Autowired @Getter private EntityManagerFactory entityManagerFactory;

    @Test
    @Sql({"classpath:sql/truncate.sql","classpath:/sql/single_todolist_with_two_todos.sql"})
    public void shouldDeleteTodoFromTodoList() {

        // When: A todo is deleted from the todolist
        // Then: The response should be correct
        ApiTodoList todoList = given()
            .standaloneSetup(controller, apiControllerAdvice)
            .spec(RequestSpecification.SPEC)
            .delete("/todolists/1/todos/2")
            .then()
            .statusCode(HttpStatus.NO_CONTENT.value())
            .extract()
            .as(ApiTodoList.class);

        // And: The returned todolist should only contain the correct todo
        assertThat(todoList.getItems().size()).isEqualTo(1);
        assertThat(todoList.getItems().get(0).getText()).isEqualTo("Existing Todo 1");

        // And: The todolist should only contain one todo in the db
        doInJPA(this::getEntityManagerFactory, em -> {
            TodoList todoListInDb = em.find(TodoList.class, 1L);
            assertThat(todoListInDb.getItems().size()).isEqualTo(1);
        });
    }
}
