package com.university.academic.student.Teacher;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TeacherRequest(
        @NotBlank(message = "El campo de la especialidad está vacío")
        @Size(min = 1, max = 100, message = "La especialidad debe contener menos de 100 caracteres")
        String specialty

) {}
