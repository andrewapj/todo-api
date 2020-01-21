package com.github.andrewapj.todoapi.feature.todolist;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.assertj.core.api.Assertions.assertThat;

import com.github.andrewapj.todoapi.api.advice.ApiControllerAdvice;
import com.github.andrewapj.todoapi.api.controller.TodoListController;
import com.github.andrewapj.todoapi.api.model.ApiError;
import com.github.andrewapj.todoapi.api.model.ApiTodoList;
import com.github.andrewapj.todoapi.domain.exception.ErrorType;
import com.github.andrewapj.todoapi.feature.spec.RequestSpecification;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class GetTodoList {

    @Autowired private TodoListController controller;
    @Autowired private ApiControllerAdvice advice;

    @Test
    @Sql({"classpath:sql/truncate.sql","classpath:/sql/single_todolist_with_two_todos.sql"})
    public void shouldGetTodoListWithTodos() {

        // When: We find a todo list with two todos
        // Then: The response should be correct.
        ApiTodoList apiTodoList = given()
            .standaloneSetup(controller,advice)
            .spec(RequestSpecification.SPEC)
            .get("todolists/1")
            .then()
            .statusCode(HttpStatus.OK.value())
            .extract().as(ApiTodoList.class);

        // And: The data should be correct.
        assertThat(apiTodoList.getItems().size()).isEqualTo(2);
    }

    @Test
    @Sql({"classpath:sql/truncate.sql","classpath:/sql/two_todolists_with_two_todos.sql"})
    public void shouldGetTodoListSummaries() {

        // When: We request all the todolists
        // Then: The response should be correct.
        List<Map<String, Integer>> summaryList = given()
            .standaloneSetup(controller,advice)
            .spec(RequestSpecification.SPEC)
            .get("todolists/")
            .then()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .jsonPath().getList("");

        // And: The response should be correct.
        assertThat(summaryList.size()).isEqualTo(2);
        assertThat(summaryList.get(0).get("todosCount")).isEqualTo(2);
        assertThat(summaryList.get(1).get("todosCount")).isEqualTo(2);
    }

    @Test
    @Sql({"classpath:sql/truncate.sql"})
    public void shouldNotGetMissingTodoList() {

        // When: We search for a todo list that does not exist.
        // Then: The response should be correct.
        ApiError apiError = given()
            .standaloneSetup(controller,advice)
            .spec(RequestSpecification.SPEC)
            .get("todolists/1")
            .then()
            .statusCode(HttpStatus.NOT_FOUND.value())
            .extract().as(ApiError.class);

        assertThat(apiError.getErrorType()).isEqualTo(ErrorType.TODOLIST_NOTFOUND);
        assertThat(apiError.getObjectId()).isEqualTo("1");
    }
}
