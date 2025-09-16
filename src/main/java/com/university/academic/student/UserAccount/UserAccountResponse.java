package com.university.academic.student.UserAccount;

import com.university.academic.student.Person.Person;

public record UserAccountResponse(
        String username,
        String email,
        String password,
        Person personId
) {
}
