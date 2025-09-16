package com.university.academic.student.Group;

import com.university.academic.student.Subject.Subject;

public record GroupResponse(
        String code,
        String schedule,
        Subject subjectId
) {
}
