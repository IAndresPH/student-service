package com.studentservice.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public record StudentResponseDTO(
    Long id,
    String code,
    Integer semester,
    String career,
    LocalDate admissionDate,
    BigDecimal average
) {}