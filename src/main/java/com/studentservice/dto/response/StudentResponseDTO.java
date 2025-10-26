package com.studentservice.dto.response;

import com.studentservice.enums.Career;

import java.math.BigDecimal;
import java.time.LocalDate;

public record StudentResponseDTO(
    Long id,
    String code,
    Integer semester,
    Career career,
    LocalDate admissionDate,
    BigDecimal average
) {}