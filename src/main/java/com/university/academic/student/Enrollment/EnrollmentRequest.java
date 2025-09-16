package com.university.academic.student.Enrollment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Date;

public record EnrollmentRequest(
        @NotBlank(message = "El campo del corte está vacío")
        int period

) {}
