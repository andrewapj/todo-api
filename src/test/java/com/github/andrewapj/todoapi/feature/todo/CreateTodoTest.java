package com.github.andrewapj.todoapi.feature.todo;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hibernate.testing.transaction.TransactionUtil.doInJPA;

import com.github.andrewapj.todoapi.api.advice.ApiControllerAdvice;
import com.github.andrewapj.todoapi.api.controller.TodoController;
import com.github.andrewapj.todoapi.api.model.ApiError;
import com.github.andrewapj.todoapi.api.model.ApiTodo;
import com.github.andrewapj.todoapi.domain.TodoList;
import com.github.andrewapj.todoapi.domain.exception.ErrorType;
import com.github.andrewapj.todoapi.factory.TestDataFactory;
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
public class CreateTodoTest {

    @Autowired private TodoController controller;
    @Autowired private ApiControllerAdvice apiControllerAdvice;
    @Autowired @Getter private EntityManagerFactory entityManagerFactory;

    @Test
    @Sql({"classpath:sql/truncate.sql","classpath:sql/empty_todolist_only.sql"})
    public void should_createNewTodo() {

        // Given: A Todo to create
        ApiTodo apiTodoToCreate = TestDataFactory.getDefaultApiTodo(null);

        // When: A request to create a new Todo is made
        ApiTodo apiTodoResponse = given()
            .standaloneSetup(controller)
            .spec(RequestSpecification.SPEC)
            .body(apiTodoToCreate)
            .post("/todolists/1/todos")
            .then()
            .statusCode(HttpStatus.CREATED.value())
            .extract().as(ApiTodo.class);

        // Then: A new todo item with the correct data and an id should be returned.
        assertThat(apiTodoResponse.getId()).isNotZero();
        assertThat(apiTodoResponse).isEqualToIgnoringGivenFields(apiTodoToCreate,"id");

        // And: The todolist should contain the todo in the DB
        doInJPA(this::getEntityManagerFactory, em -> {
            assertThat(em.find(TodoList.class,1L)).isNotNull();
            assertThat(em.find(TodoList.class,1L).getItems()).isNotEmpty();
        });
    }

    @Test
    @Sql("classpath:sql/truncate.sql")
    public void should_NotCreateTodo_WhenTodoListMissing() {

        // Given: A todo to save and add
        ApiTodo apiTodoToCreate = TestDataFactory.getDefaultApiTodo(null);

        // When: A request to create a new Todo is made
        ApiError apiError = given()
            .standaloneSetup(controller, apiControllerAdvice)
            .spec(RequestSpecification.SPEC)
            .body(apiTodoToCreate)
            .post("/todolists/1/todos")
            .then()
            .statusCode(HttpStatus.NOT_FOUND.value())
            .extract()
            .as(ApiError.class);

        //Then: The error should be correct
        assertThat(apiError.getErrorType()).isEqualTo(ErrorType.TODOLIST_NOTFOUND);
        assertThat(apiError.getObjectId()).isEqualTo("1");
        assertThat(apiError.getTimestamp()).isNotBlank();
    }
}
