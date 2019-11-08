package com.github.andrewapj.todoapi.api.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class ApiTodoTest {

    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(ApiTodo.class).verify();
    }
}
