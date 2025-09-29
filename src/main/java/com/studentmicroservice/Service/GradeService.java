package com.studentmicroservice.Service;

import com.studentmicroservice.Controller.dto.GradeRequest;
import com.studentmicroservice.Controller.dto.GradesSummaryResponse;
import com.studentmicroservice.Entity.Grade;

import java.util.List;

public interface GradeService {
    Grade create(GradeRequest request);
    List<Grade> listByStudent(Long studentId);
    GradesSummaryResponse summary(Long studentId);
    List<SemesterAverage> progressBySemester(Long studentId);

    record SemesterAverage(Integer semester, java.math.BigDecimal average) {}
}
