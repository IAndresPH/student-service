package com.university.academic.student.Subject;


import java.util.List;
import java.util.Optional;

public interface ISubjectService {
    List<Subject> findAll();
    Optional<Subject> findById(int id);
    Subject save(Subject subject);
    void update(Subject subject, int id);
    void delete(int id);
}
