package com.studentservice.entity;

import com.studentservice.enums.Gender;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class StudentEntityTest {

    @Test
    void gettersAndSetters_coverAllFields() {
        Student s = new Student();

        s.setId(10L);
        s.setFirstName("Ada");
        s.setLastName("Lovelace");
        s.setEmail("ada.lovelace@example.com");
        s.setBirthDate(LocalDate.of(1990,12,10));
        s.setGender(Gender.FEMENINO);
        s.setPhone("3001234567");
        s.setAddress("Calle 123 #45-67");
        s.setCode("STU-001");
        s.setSemester(3);
        s.setCareer("Ingeniería");
        s.setAdmissionDate(LocalDate.of(2020,1,15));
        s.setAverage(new BigDecimal("4.5"));

        assertEquals(10L, s.getId());
        assertEquals("Ada", s.getFirstName());
        assertEquals("Lovelace", s.getLastName());
        assertEquals("ada.lovelace@example.com", s.getEmail());
        assertEquals(LocalDate.of(1990,12,10), s.getBirthDate());
        assertEquals(Gender.FEMENINO, s.getGender());
        assertEquals("3001234567", s.getPhone());
        assertEquals("Calle 123 #45-67", s.getAddress());
        assertEquals("STU-001", s.getCode());
        assertEquals(3, s.getSemester());
        assertEquals("Ingeniería", s.getCareer());
        assertEquals(LocalDate.of(2020,1,15), s.getAdmissionDate());
        assertEquals(new BigDecimal("4.5"), s.getAverage());
    }
}
