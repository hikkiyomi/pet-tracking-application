package ru.hikkiyomi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.hikkiyomi.controllers.KittenController;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * A sanity check test for checking if Spring Boot works and autowires correctly.
 */
@SpringBootTest
public class SanityCheckTest {
    @Autowired
    private KittenController controller;

    @Test
    void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }
}
