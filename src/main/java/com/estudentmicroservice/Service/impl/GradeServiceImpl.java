package com.estudentmicroservice.Service.impl;

import com.estudentmicroservice.Controller.dto.GradeRequest;
import com.estudentmicroservice.Controller.dto.GradesSummaryResponse;
import com.estudentmicroservice.Controller.dto.SubjectComparison;
import com.estudentmicroservice.Entity.Grade;
import com.estudentmicroservice.Entity.Student;
import com.estudentmicroservice.Repository.GradeRepository;
import com.estudentmicroservice.Repository.StudentRepository;
import com.estudentmicroservice.Service.GradeService;
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
                .collect(Collectors.toMap(Grade::getSubject, Grade::getScore, (a,b)->a));

        Number myAvgNum = gradeRepo.studentOverallAvg(studentId);
        Number grpAvgNum = gradeRepo.groupOverallAvgForStudentSubjects(studentId);

        Map<String, BigDecimal> groupAvg = new HashMap<>();
        for (Map<String,Object> row : gradeRepo.groupAvgPerSubject(studentId)) {
            groupAvg.put((String) row.get("subject"), new BigDecimal(row.get("avg").toString()));
        }

        Map<String, int[]> ranks = new HashMap<>();
        for (Map<String,Object> row : gradeRepo.rankPerSubject(studentId)) {
            String subject = (String) row.get("subject");
            Integer rnk = ((Number) row.get("rnk")).intValue();
            Integer total = ((Number) row.get("total")).intValue();
            ranks.put(subject, new int[]{rnk, total});
        }

        List<SubjectComparison> list = myBySubject.keySet().stream().sorted()
                .map(sub -> new SubjectComparison(
                        sub,
                        myBySubject.get(sub),
                        groupAvg.getOrDefault(sub, null),
                        ranks.containsKey(sub) ? ranks.get(sub)[0] : null,
                        ranks.containsKey(sub) ? ranks.get(sub)[1] : null
                ))
                .toList();

        BigDecimal myAvg = myAvgNum == null ? null : new BigDecimal(myAvgNum.toString());
        BigDecimal groupAvgAll = grpAvgNum == null ? null : new BigDecimal(grpAvgNum.toString());

        return new GradesSummaryResponse(studentId, myAvg, groupAvgAll, list);
    }
}
