package com.university.academic.student.Report;


import java.util.List;
import java.util.Optional;

public interface IReportService {
    List<Report> findAll();
    Optional<Report> findById(int id);
    Report save(Report report);
    void update(Report report, int id);
    void delete(int id);
}
