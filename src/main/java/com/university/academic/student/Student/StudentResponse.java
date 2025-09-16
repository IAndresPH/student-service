package com.university.academic.student.Student;

import com.university.academic.student.Person.Person;

import java.math.BigDecimal;

public record StudentResponse(
        String code,
        int semester,
        String career,
        BigDecimal gpa,
        Person personId
) {
}
