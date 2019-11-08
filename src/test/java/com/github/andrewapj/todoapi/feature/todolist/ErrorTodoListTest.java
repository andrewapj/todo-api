package com.github.andrewapj.todoapi.feature.todolist;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.github.andrewapj.todoapi.api.advice.ApiControllerAdvice;
import com.github.andrewapj.todoapi.api.controller.TodoListController;
import com.github.andrewapj.todoapi.api.model.ApiError;
import com.github.andrewapj.todoapi.domain.exception.ErrorType;
import com.github.andrewapj.todoapi.domain.exception.PersistenceException;
import com.github.andrewapj.todoapi.factory.ClockFactory;
import com.github.andrewapj.todoapi.feature.spec.RequestSpecification;
import com.github.andrewapj.todoapi.service.TodoListPersistenceService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ErrorTodoListTest {

    private static final String PATH = "todolists/";

    @Autowired private TodoListController controller;
    @Autowired private ApiControllerAdvice apiControllerAdvice;
    @MockBean private TodoListPersistenceService mockPersistenceService;

    @Test
    public void should_GetErrorResponseFromCreate_WithPersistenceException() {

        final String objectId = "1";

        // Given: An error object we expect to be returned from the API
        ApiError expectedError =  ApiError.builder()
            .errorType(ErrorType.CREATE_TODOLIST)
            .objectId(objectId)
            .timestamp(String.valueOf(ClockFactory.getDefaultFixedClock().millis()))
            .build();

        // And: The persistence service will throw a Persistence Exception
        when(mockPersistenceService.create())
            .thenThrow(PersistenceException.builder()
                .errorType(ErrorType.CREATE_TODOLIST)
                .objectId(objectId)
                .timestamp(String.valueOf(ClockFactory.getDefaultFixedClock().millis()))
                .build());

        // When: A request to create a new TodoList is made
        // Then: The HTTP response should be correct
        ApiError apiError =  given()
            .standaloneSetup(controller,apiControllerAdvice)
            .spec(RequestSpecification.SPEC)
            .post(PATH)
            .then()
            .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .extract()
            .as(ApiError.class);

        // And: The error object should be correct.
        assertThat(apiError).isEqualTo(expectedError);
    }
}
