package com.university.academic.student.Notification;

import com.university.academic.student.Person.Person;

import java.util.Date;

public record NotificationResponse(
        String message,
        Date date,
        Person personId
) {
}
