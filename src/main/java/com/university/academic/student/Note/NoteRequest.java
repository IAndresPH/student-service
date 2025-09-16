package com.university.academic.student.Note;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record NoteRequest(
        @NotBlank(message = "El campo del valor está vacío")
        BigDecimal value,

        @NotBlank(message = "El campo de la observación está vacío")
        @Size(min = 1, max = 250, message = "La observación debe contener menos de 250 caracteres")
        String observation

) {}
