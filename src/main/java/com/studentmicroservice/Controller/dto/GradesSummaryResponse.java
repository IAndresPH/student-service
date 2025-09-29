package com.studentmicroservice.Controller.dto;

import java.math.BigDecimal;
import java.util.List;

public record GradesSummaryResponse(
        Long studentId,
        BigDecimal myAverage,
        BigDecimal groupAverage,
        List<SubjectComparison> subjects
) {}
