package ru.otus;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;

@Testcontainers
class HomeWorkOrmIT {


    @Container
    PostgreSQLContainer<?> postgres = postgres();


    @Test
    void main_methodExecutingWithoutException() {
        Assertions.assertDoesNotThrow(() -> HomeWorkOrm.main(null));
    }

    private PostgreSQLContainer<?> postgres() {
        PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16")
                .withDatabaseName("demoDB")
                .withUsername("usr")
                .withPassword("pwd");
        postgres.setPortBindings(new ArrayList<>() {{
            add("5430:5432");
        }});
        return postgres;
    }
}