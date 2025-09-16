package com.university.academic.student.Subject;

import com.university.academic.student.Program.Program;

public record SubjectResponse(
        String name,
        int credits,
        Program programId
) {
}
