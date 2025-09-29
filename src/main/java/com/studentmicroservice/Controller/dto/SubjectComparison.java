package com.studentmicroservice.Controller.dto;

import java.math.BigDecimal;

public record SubjectComparison(
        String subject,
        BigDecimal myScore,
        BigDecimal groupAverage,
        Integer rank,
        Integer total
) {}
