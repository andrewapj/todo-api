package com.github.andrewapj.todoapi.feature.todolist;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hibernate.testing.transaction.TransactionUtil.doInJPA;

import com.github.andrewapj.todoapi.api.advice.ApiControllerAdvice;
import com.github.andrewapj.todoapi.api.controller.TodoListController;
import com.github.andrewapj.todoapi.api.model.ApiError;
import com.github.andrewapj.todoapi.api.model.EmptyResponse;
import com.github.andrewapj.todoapi.domain.Todo;
import com.github.andrewapj.todoapi.domain.TodoList;
import com.github.andrewapj.todoapi.domain.exception.ErrorType;
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
public class DeleteTodoListTest {

    @Autowired private TodoListController controller;
    @Autowired private ApiControllerAdvice apiControllerAdvice;
    @Autowired @Getter private EntityManagerFactory entityManagerFactory;

    @Test
    @Sql({"classpath:sql/truncate.sql","classpath:/sql/single_todolist_with_two_todos.sql"})
    public void shouldDeleteTodoListAndAllTodos() {

        // When: We delete the todolist
        // Then: We should get the correct response
        given()
            .standaloneSetup(controller, apiControllerAdvice)
            .spec(RequestSpecification.SPEC)
            .delete("/todolists/1")
            .then()
            .statusCode(HttpStatus.NO_CONTENT.value())
            .extract().as(EmptyResponse.class);

        // And: The todolist and the todos should not be in the DB.
        doInJPA(this::getEntityManagerFactory, em -> {
            assertThat(em.find(TodoList.class, 1L)).isNull();
            assertThat(em.find(Todo.class, 1L)).isNull();
            assertThat(em.find(Todo.class, 2L)).isNull();
        });
    }

    @Test
    @Sql({"classpath:sql/truncate.sql"})
    public void shouldNotDeleteMissingTodolist() {

        // When: We delete a todolist that doesnt exist.
        // Then: The response should be correct
        ApiError apiError = given()
            .standaloneSetup(controller, apiControllerAdvice)
            .spec(RequestSpecification.SPEC)
            .delete("/todolists/1")
            .then()
            .statusCode(HttpStatus.NOT_FOUND.value())
            .extract()
            .as(ApiError.class);

        // And: The error should be correct.
        assertThat(apiError.getErrorType()).isEqualTo(ErrorType.TODOLIST_NOTFOUND);
        assertThat(apiError.getObjectId()).isEqualTo("1");
    }
}
