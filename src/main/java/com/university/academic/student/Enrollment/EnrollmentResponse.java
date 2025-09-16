package com.university.academic.student.Enrollment;

public record EnrollmentResponse(
        String name,
        String surname,
        String document,
        String phone
) {
}
