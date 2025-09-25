package com.studentmicroservice.Service.impl;

import com.studentmicroservice.Controller.dto.GradeRequest;
import com.studentmicroservice.Controller.dto.GradesSummaryResponse;
import com.studentmicroservice.Controller.dto.SubjectComparison;
import com.studentmicroservice.Entity.Grade;
import com.studentmicroservice.Entity.Student;
import com.studentmicroservice.Repository.GradeRepository;
import com.studentmicroservice.Repository.StudentRepository;
import com.studentmicroservice.Service.GradeService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GradeServiceImpl implements GradeService {

    private final GradeRepository gradeRepo;
    private final StudentRepository studentRepo;

    public GradeServiceImpl(GradeRepository gradeRepo, StudentRepository studentRepo) {
        this.gradeRepo = gradeRepo;
        this.studentRepo = studentRepo;
    }

    @Override
    public Grade create(GradeRequest req) {
        Student s = studentRepo.findById(req.studentId()).orElseThrow();
        Grade g = new Grade();
        g.setStudent(s);
        g.setSubject(req.subject());
        g.setScore(req.score());
        g.setSemester(req.semester());
        return gradeRepo.save(g);
    }

    @Override
    public List<Grade> listByStudent(Long studentId) {
        return gradeRepo.findByStudentId(studentId);
    }

    @Override
    public GradesSummaryResponse summary(Long studentId) {
        List<Grade> myGrades = gradeRepo.findByStudentId(studentId);
        Map<String, BigDecimal> myBySubject = myGrades.stream()
                .collect(Collectors.toMap(Grade::getSubject, Grade::getScore, (a, b) -> a));

        // Compute student's overall average locally
        BigDecimal myAvg = myGrades.isEmpty() ? null :
                new BigDecimal(
                        myGrades.stream()
                                .map(Grade::getScore)
                                .map(java.math.BigDecimal::toString)
                                .map(java.math.BigDecimal::new)
                                .map(java.math.BigDecimal::doubleValue)
                                .mapToDouble(Double::doubleValue)
                                .average()
                                .orElse(Double.NaN)
                ).setScale(1, java.math.RoundingMode.HALF_UP);

        // Group comparison and ranks are not available without group context; return nulls
        List<SubjectComparison> list = myBySubject.keySet().stream().sorted()
                .map(sub -> new SubjectComparison(sub, myBySubject.get(sub), null, null, null))
                .toList();

        return new GradesSummaryResponse(studentId, myAvg, null, list);
    }

    @Override
    public List<GradeService.SemesterAverage> progressBySemester(Long studentId) {
        return gradeRepo.averageBySemester(studentId)
                .stream()
                .map(arr -> new GradeService.SemesterAverage(
                        ((Number) arr[0]).intValue(),
                        new java.math.BigDecimal(arr[1].toString())
                ))
                .toList();
    }
}
