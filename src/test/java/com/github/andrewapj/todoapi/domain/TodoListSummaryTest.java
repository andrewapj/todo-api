package com.github.andrewapj.todoapi.domain;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class TodoListSummaryTest {

    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(TodoListSummary.class).verify();
    }
}
