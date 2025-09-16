package com.university.academic.student.Program;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ProgramRequest(
        @NotBlank(message = "El campo del nombre está vacío")
        @Size(min = 1, max = 100, message = "El nombre debe contener menos de 100 caracteres")
        String name,

        @NotBlank(message = "El campo de la facultad está vacío")
        @Size(min = 1, max = 100, message = "El nombre de la facultad debe contener menos de 100 caracteres")
        String faculty

) {}
