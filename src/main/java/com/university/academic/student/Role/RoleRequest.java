package com.university.academic.student.Role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RoleRequest(
        @NotBlank(message = "El campo del nombre está vacío")
        @Size(min = 1, max = 50, message = "El nombre debe contener menos de 50 caracteres")
        String name,

        @NotBlank(message = "El campo de la descripción está vacío")
        @Size(min = 1, max = 250, message = "La descripción debe contener menos de 250 caracteres")
        String description
) {}
