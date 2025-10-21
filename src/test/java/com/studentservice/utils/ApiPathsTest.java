package com.studentservice.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ApiPathsTest {

    @Test
    void studentsPath_ok() {
        assertEquals("/api/v1/students", ApiPaths.STUDENTS);
    }

    @Test
    void baseApiPath_ok() {
        assertEquals("/api/v1", ApiPaths.BASE_API);
    }
}
