package com.university.academic.student.Teacher;


import java.util.List;
import java.util.Optional;

public interface ITeacherService {
    List<Teacher> findAll();
    Optional<Teacher> findById(int id);
    Teacher save(Teacher teacher);
    void update(Teacher teacher, int id);
    void delete(int id);
}
