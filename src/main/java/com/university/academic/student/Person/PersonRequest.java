package com.university.academic.student.Person;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PersonRequest(
        @NotBlank(message = "El campo del nombre está vacío")
        @Size(min = 1, max = 50, message = "El nombre debe contener menos de 50 caracteres")
        String name,

        @NotBlank(message = "El campo del apellido está vacío")
        @Size(min = 1, max = 50, message = "El apellido debe contener menos de 50 caracteres")
        String surname,

        @NotBlank(message = "El campo del documento está vacío")
        @Size(min = 1, max = 20, message = "El documento debe contener menos de 20 caracteres")
        String document,

        @NotBlank(message = "El campo del teléfono está vacío")
        @Size(min = 1, max = 20, message = "El teléfono debe contener menos de 20 caracteres")
        String phone

) {}
