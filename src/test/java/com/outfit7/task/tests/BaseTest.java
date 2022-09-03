package com.outfit7.task.tests;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;

@Slf4j
public class BaseTest {
    private long startTime;

    @BeforeEach
    void beforeEachTest(TestInfo testInfo) {
        startTime = System.currentTimeMillis();
        log.info(String.format("Starting test: %s", testInfo.getDisplayName()));
    }

    @AfterEach
    void afterEachTest(TestInfo testInfo) {
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        log.info (String.format("Finished executing %s", testInfo.getDisplayName()));
        log.info("Test runtime was: " + (duration >= 1000L ? duration / 1000L + " seconds" : duration + "milliseconds"));
    }
}
