package com.github.andrewapj.todoapi.feature.todo;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hibernate.testing.transaction.TransactionUtil.doInJPA;

import com.github.andrewapj.todoapi.api.advice.ApiControllerAdvice;
import com.github.andrewapj.todoapi.api.controller.TodoController;
import com.github.andrewapj.todoapi.api.model.ApiError;
import com.github.andrewapj.todoapi.api.model.ApiTodo;
import com.github.andrewapj.todoapi.domain.Todo;
import com.github.andrewapj.todoapi.domain.TodoList;
import com.github.andrewapj.todoapi.domain.exception.ErrorType;
import com.github.andrewapj.todoapi.factory.TestDataFactory;
import com.github.andrewapj.todoapi.feature.spec.RequestSpecification;
import io.restassured.module.mockmvc.response.MockMvcResponse;
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
public class UpdateTodoTest {

    @Autowired
    private TodoController controller;
    @Autowired
    private ApiControllerAdvice apiControllerAdvice;
    @Autowired
    @Getter
    private EntityManagerFactory entityManagerFactory;

    @Test
    @Sql({"classpath:sql/truncate.sql", "classpath:/sql/single_todolist_with_single_todo.sql"})
    public void should_UpdateTodo() {

        // Given: A todo to update
        ApiTodo apiTodoToUpdate = TestDataFactory.getDefaultApiTodo(1L);

        // When: The update request id made
        ApiTodo returnedTodo = makeUpdateRequest(apiTodoToUpdate)
            .then()
            .statusCode(HttpStatus.OK.value())
            .extract().as(ApiTodo.class);

        // Then: The returned object should be correct
        assertThat(returnedTodo).isEqualTo(apiTodoToUpdate);

        // And: The correct data should be in the DB
        doInJPA(this::getEntityManagerFactory, em -> {
            assertThat(em.find(TodoList.class, 1L).getItems()).isNotEmpty();
            assertThat(em.find(Todo.class, 1L).getText()).isEqualTo("A new Todo");
        });
    }

    @Test
    @Sql("classpath:sql/truncate.sql")
    public void should_GetNotFound_WhenUpdatingMissingTodo_WithAMissingTodoList() {

        // Given: A todo to update
        ApiTodo apiTodoToUpdate = TestDataFactory.getDefaultApiTodo(1L);

        // When: We update a todo that does not exist in the DB.
        ApiError apiError = makeUpdateRequest(apiTodoToUpdate)
            .then()
            .statusCode(HttpStatus.NOT_FOUND.value())
            .extract()
            .as(ApiError.class);

        //Then: The error should be correct
        assertThat(apiError.getErrorType()).isEqualTo(ErrorType.TODOLIST_NOTFOUND);
        assertThat(apiError.getObjectId()).isEqualTo("1");
        assertThat(apiError.getTimestamp()).isNotBlank();
    }

    @Test
    @Sql({"classpath:sql/truncate.sql", "classpath:sql/empty_todolist_only.sql"})
    public void should_GetNotFound_WhenUpdatingMissingTodo() {

        // Given: A todo to update
        ApiTodo apiTodoToUpdate = TestDataFactory.getDefaultApiTodo(1L);

        // When: We update a todo that does not exist in the DB.
        ApiError apiError = makeUpdateRequest(apiTodoToUpdate)
            .then()
            .statusCode(HttpStatus.NOT_FOUND.value())
            .extract()
            .as(ApiError.class);

        //Then: The error should be correct
        assertThat(apiError.getErrorType()).isEqualTo(ErrorType.TODO_NOTFOUND);
        assertThat(apiError.getObjectId()).isEqualTo("1");
        assertThat(apiError.getTimestamp()).isNotBlank();
    }

    public MockMvcResponse makeUpdateRequest(final ApiTodo todo) {
        return given()
            .standaloneSetup(controller, apiControllerAdvice)
            .spec(RequestSpecification.SPEC)
            .body(todo)
            .put("/todolists/1/todos/1");
    }
}
