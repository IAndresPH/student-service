package com.university.academic.student.Activity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ActivityRequest(
        @NotBlank(message = "El campo del nombre está vacío")
        @Size(min = 1, max = 50, message = "El nombre debe contener menos de 50 caracteres")
        String name,

        @NotBlank(message = "El campo del porcentaje está vacío")
        BigDecimal percentage

) {}
