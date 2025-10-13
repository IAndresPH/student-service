package com.studentservice.exception;

import static com.studentservice.utils.MessageConstants.STUDENT_NOT_FOUND;

public class StudentNotFoundException extends RuntimeException{

    public StudentNotFoundException() {
        super(STUDENT_NOT_FOUND);
    }
}