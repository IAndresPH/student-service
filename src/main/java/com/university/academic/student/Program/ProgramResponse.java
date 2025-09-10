package com.university.academic.student.Program;

import com.university.academic.student.University.University;

public record ProgramResponse(
        String name,
        String faculty,
        University universityId
) {
}
