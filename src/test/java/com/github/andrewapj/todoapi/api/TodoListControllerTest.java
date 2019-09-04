package com.github.andrewapj.todoapi.api;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.github.andrewapj.todoapi.api.mapper.TodoListMapper;
import com.github.andrewapj.todoapi.api.model.ApiTodoList;
import com.github.andrewapj.todoapi.domain.factory.TodoListFactory;
import com.github.andrewapj.todoapi.feature.spec.RequestSpecification;
import com.github.andrewapj.todoapi.service.TodoListPersistenceService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TodoListControllerTest {

    private static final String PATH = "todolists/";

    @Autowired private TodoListMapper mapper;
    @MockBean private TodoListPersistenceService persistenceService;

    @Test
    public void shouldCreateNewTodoList() {

        when(persistenceService.create()).thenReturn(TodoListFactory.getNewTodoList(1L));

        ApiTodoList todoList = given()
            .standaloneSetup(new TodoListController(persistenceService, mapper))
            .spec(RequestSpecification.SPEC)
            .post(PATH)
            .then()
            .extract()
            .as(ApiTodoList.class);

        assertThat(todoList.getId()).isNotZero();
    }
}
