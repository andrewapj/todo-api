package com.github.andrewapj.todoapi.api.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.github.andrewapj.todoapi.domain.exception.ErrorType;
import lombok.Builder;
import lombok.Value;

/**
 * Bean to represent an error within the API.
 */
@Value
@Builder
@JsonDeserialize(builder = ApiError.ApiErrorBuilder.class)
public final class ApiError {

    private final ErrorType errorType;
    private final String objectId;
    private final String timestamp;

    /**
     * Builder for this bean that also maintains compatibility with Jackson.
     */
    @JsonPOJOBuilder(withPrefix = "")
    public static final class ApiErrorBuilder {
    }
}
