package com.studentservice.dto.request;

import com.studentservice.enums.Gender;
import com.studentservice.enums.Career;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

public record StudentRequestDTO(
    @NotBlank @Size(max = 50)
    String firstName,

    @NotBlank @Size(max = 50)
    String lastName,

    @Email @NotBlank
    String email,

    @NotNull
    LocalDate birthDate,

    @NotNull
    Gender gender,

    @Size(max = 20)
    String phone,

    @Size(max = 150)
    String address,

    @NotBlank @Size(max = 30)
    String code,

    @NotNull @Min(1)
    Integer semester,

    @NotBlank @Size(max = 80)
    Career career,

    @NotNull
    LocalDate admissionDate,

    @DecimalMin("0.0") @DecimalMax("5.0")
    BigDecimal average
) {}