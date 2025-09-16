package com.university.academic.student.Activity;

import com.university.academic.student.Subject.Subject;

import java.math.BigDecimal;

public record ActivityResponse(
        String name,
        BigDecimal percentage,
        Subject subjectId
) {
}
