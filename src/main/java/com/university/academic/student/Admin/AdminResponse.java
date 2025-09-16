package com.university.academic.student.Admin;

import com.university.academic.student.Student.Student;

import java.util.Date;

public record AdminResponse(
        int period,
        Date date,
        Student studentId
) {
}
