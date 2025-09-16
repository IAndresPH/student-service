package com.university.academic.student.UserAccount;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserAccountRequest(
        @NotBlank(message = "El campo del nombre de usuario está vacío")
        @Size(min = 1, max = 50, message = "El nombre de usuario debe contener menos de 50 caracteres")
        String username,

        @NotBlank(message = "El campo del correo está vacío")
        @Size(min = 1, max = 100, message = "El correo debe contener menos de 100 caracteres")
        String email,

        @NotBlank(message = "El campo de la contraseña está vacío")
        @Size(min = 10, max = 50, message = "La contraseña debe contener entre 10 y 50 caracteres")
        String password
) {
}
