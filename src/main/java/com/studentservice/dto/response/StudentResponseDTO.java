package com.studentservice.dto.response;

import com.studentservice.enums.Gender;
import com.studentservice.enums.Career;
import java.math.BigDecimal;
import java.time.LocalDate;

public record StudentResponseDTO(
    Long id,
    String firstName,
    String lastName,
    String email,
    LocalDate birthDate,
    Integer age,
    Gender gender,
    String phone,
    String address,
    String code,
    Integer semester,
    Career career,
    LocalDate admissionDate,
    BigDecimal average
) {}