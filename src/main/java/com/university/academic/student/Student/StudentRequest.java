package com.university.academic.student.Student;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record StudentRequest(
        @NotBlank(message = "El campo del código está vacío")
        @Size(min = 1, max = 30, message = "El código debe contener menos de 100 caracteres")
        String code,

        @NotBlank(message = "El campo del semestre está vacío")
        int semester,

        @NotBlank(message = "El campo de la carrera está vacío")
        @Size(min = 1, max = 50, message = "La carrera debe contener menos de 50 caracteres")
        String career,

        @NotBlank(message = "El campo del promedio ponderado está vacío")
        BigDecimal gpa

) {}
