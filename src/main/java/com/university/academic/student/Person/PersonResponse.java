package com.university.academic.student.Person;

public record PersonResponse(
        String name,
        String surname,
        String document,
        String phone
) {
}
