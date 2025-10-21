package com.studentservice.handler;

import com.studentservice.exception.StudentNotFoundException;
import com.studentservice.exception.handler.GlobalExceptionHandler;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleStudentNotFound_returns404Body() {
        ResponseEntity<Map<String, Object>> resp =
                handler.handleStudentNotFound(new StudentNotFoundException());

        assertEquals(404, resp.getStatusCode().value());
        assertTrue(resp.getBody().get("message").toString().contains("Estudiante no encontrado"));
        assertTrue(resp.getBody().containsKey("timestamp"));
        assertTrue(resp.getBody().containsKey("status"));
        assertTrue(resp.getBody().containsKey("error"));
    }

    @Test
    void handleGeneric_returns500Body() {
        ResponseEntity<Map<String, Object>> resp =
                handler.handleGeneric(new RuntimeException("boom"));

        assertEquals(500, resp.getStatusCode().value());
        assertTrue(resp.getBody().get("message").toString().contains("boom"));
        assertTrue(resp.getBody().containsKey("timestamp"));
        assertTrue(resp.getBody().containsKey("status"));
        assertTrue(resp.getBody().containsKey("error"));
    }
}
