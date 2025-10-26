package com.studentservice.entity;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class StudentEntityTest {

    @Test
    void gettersAndSetters_coverAllFields() {
        Student s = new Student();

        s.setId(10L);
        s.setCode("STU-001");
        s.setSemester(3);
        s.setCareer("Ingeniería");
        s.setAdmissionDate(LocalDate.of(2020,1,15));
        s.setAverage(new BigDecimal("4.5"));

        assertEquals(10L, s.getId());
        assertEquals("STU-001", s.getCode());
        assertEquals(3, s.getSemester());
        assertEquals("Ingeniería", s.getCareer());
        assertEquals(LocalDate.of(2020,1,15), s.getAdmissionDate());
        assertEquals(new BigDecimal("4.5"), s.getAverage());
    }
}
