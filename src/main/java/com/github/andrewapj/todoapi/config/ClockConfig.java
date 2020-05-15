package com.github.andrewapj.todoapi.config;

import java.time.Clock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class to build a {@link Clock}.
 */
@Configuration
public class ClockConfig {

    /**
     * Builds a new Clock.
     *
     * @return The new clock instance.
     */
    @Bean
    public Clock getClock() {
        return Clock.systemUTC();
    }
}
