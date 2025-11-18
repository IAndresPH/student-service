package com.studentservice;

import com.studentservice.controller.StudentController;
import com.studentservice.exception.handler.GlobalExceptionHandler;
import com.studentservice.mapper.StudentMapper;
import com.studentservice.repository.StudentRepository;
import com.studentservice.service.StudentService;
import com.studentservice.service.impl.IStudentService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.context.ApplicationContext;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StudentServiceApplicationTests {

    @Autowired
    private ApplicationContext context;

    @MockitoBean
    private StudentRepository studentRepository;

    @Test
    void contextLoads() {
        assertNotNull(context);
    }

    @DisplayName("Todos los beans clave deben estar presentes en el contexto")
    @ParameterizedTest(name = "Bean presente por tipo: {0}")
    @MethodSource("beanTypes")
    void beansShouldBePresentByType(Class<?> type) {
        Object bean = assertDoesNotThrow(
                () -> context.getBean(type),
                () -> "No se pudo resolver bean de tipo: " + type.getName()
        );
        assertNotNull(bean, "El bean no debería ser nulo");
        assertTrue(type.isInstance(bean) || type.isAssignableFrom(bean.getClass()),
                "El bean obtenido no es del tipo esperado (puede ser un proxy)");
    }

    static Stream<Class<?>> beanTypes() {
        return Stream.of(
                StudentController.class,
                IStudentService.class,
                StudentService.class,
                StudentMapper.class,
                StudentRepository.class,
                GlobalExceptionHandler.class
        );

    }
}
