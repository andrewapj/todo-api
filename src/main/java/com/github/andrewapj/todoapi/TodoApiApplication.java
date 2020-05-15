package com.github.andrewapj.todoapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Application class.
 */
@SpringBootApplication
public class TodoApiApplication {

    /**
     * Application entry point.
     *
     * @param args application arguments.
     */
    public static void main(final String[] args) {
        SpringApplication.run(TodoApiApplication.class, args);
    }
}
