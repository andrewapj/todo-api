package com.github.andrewapj.todoapi.feature.spec;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.specification.MockMvcRequestSpecBuilder;
import io.restassured.module.mockmvc.specification.MockMvcRequestSpecification;

/**
 * A Rest Assured Specification to set baseline attributes used when the test framework makes http
 * requests to the application.
 */
public class RequestSpecification {

    private static final String CONTENT_TYPE = ContentType.JSON.toString();
    private static final String BASE_PATH = "/";

    public static final MockMvcRequestSpecification SPEC = new MockMvcRequestSpecBuilder()
        .setContentType(CONTENT_TYPE)
        .setBasePath(BASE_PATH)
        .build();
}
