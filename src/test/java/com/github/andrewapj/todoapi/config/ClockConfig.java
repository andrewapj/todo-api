package com.github.andrewapj.todoapi.config;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class to build a {@link Clock}.
 */
@Configuration
public class ClockConfig {

    @Bean("fixed")
    public Clock getClock() {
        return Clock.fixed(
            LocalDateTime.of(2019, Month.SEPTEMBER, 24, 18, 0)
                .atZone(ZoneOffset.UTC)
                .toInstant(),
            ZoneOffset.UTC);
    }
}
