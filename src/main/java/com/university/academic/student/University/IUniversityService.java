package com.university.academic.student.University;


import java.util.List;
import java.util.Optional;

public interface IUniversityService {
    List<University> findAll();
    Optional<University> findById(int id);
    University save(University university);
    void update(University university, int id);
    void delete(int id);
}
