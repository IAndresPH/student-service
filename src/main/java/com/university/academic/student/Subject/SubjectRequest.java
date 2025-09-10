package com.university.academic.student.Subject;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SubjectRequest(
        @NotBlank(message = "El campo del nombre está vacío")
        @Size(min = 1, max = 100, message = "El nombre debe contener menos de 100 caracteres")
        String name,

        @NotBlank(message = "El campo de la cantidad de créditos está vacío")
        int credits

) {}
