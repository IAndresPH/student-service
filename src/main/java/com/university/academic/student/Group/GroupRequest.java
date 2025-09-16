package com.university.academic.student.Group;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record GroupRequest(
        @NotBlank(message = "El campo del código está vacío")
        @Size(min = 1, max = 30, message = "El código debe contener menos de 30 caracteres")
        String code,

        @NotBlank(message = "El campo del horario está vacío")
        @Size(min = 1, max = 50, message = "El horario debe contener menos de 50 caracteres")
        String schedule

) {}
