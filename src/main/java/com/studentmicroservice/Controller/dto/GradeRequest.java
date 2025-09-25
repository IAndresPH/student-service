package com.studentmicroservice.Controller.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record GradeRequest(
        @NotNull Long studentId,
        @NotBlank String subject,
        @NotNull @DecimalMin("0.0") @DecimalMax("5.0") BigDecimal score,
        @NotNull @Min(1) Integer semester
) {}
