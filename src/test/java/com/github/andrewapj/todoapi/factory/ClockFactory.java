package com.github.andrewapj.todoapi.factory;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;

/**
 * Test factory to build a fixed {@link Clock} for unit testing.
 */
public class ClockFactory {

    public static Clock getDefaultFixedClock() {
        return Clock.fixed(
            LocalDateTime.of(2019, Month.SEPTEMBER, 24, 18, 0)
                .atZone(ZoneOffset.UTC)
                .toInstant(),
            ZoneOffset.UTC);
    }
}
