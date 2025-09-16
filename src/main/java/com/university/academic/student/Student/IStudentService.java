package com.university.academic.student.Student;


import java.util.List;
import java.util.Optional;

public interface IStudentService {
    List<Student> findAll();
    Optional<Student> findById(int id);
    Student save(Student student);
    void update(Student student, int id);
    void delete(int id);
}
