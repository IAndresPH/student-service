package com.university.academic.student.Notification;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Date;

public record NotificationRequest(
        @NotBlank(message = "El campo del mensaje está vacío")
        @Size(min = 1, max = 30, message = "El nombre debe contener menos de 50 caracteres")
        String message

) {}
