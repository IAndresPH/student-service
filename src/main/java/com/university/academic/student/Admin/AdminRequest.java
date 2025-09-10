package com.university.academic.student.Admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AdminRequest(
        @NotBlank(message = "El campo del nivel de acceso está vacío")
        int accessLevel

) {}
