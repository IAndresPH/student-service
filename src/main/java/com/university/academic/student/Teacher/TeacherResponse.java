package com.university.academic.student.Teacher;

import com.university.academic.student.Person.Person;

public record TeacherResponse(
        String specialty,
        Person personId
) {
}
