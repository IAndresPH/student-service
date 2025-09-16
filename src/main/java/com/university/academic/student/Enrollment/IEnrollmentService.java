package com.university.academic.student.Enrollment;


import java.util.List;
import java.util.Optional;

public interface IEnrollmentService {
    List<Enrollment> findAll();
    Optional<Enrollment> findById(int id);
    Enrollment save(Enrollment enrollment);
    void update(Enrollment enrollment, int id);
    void delete(int id);
}
