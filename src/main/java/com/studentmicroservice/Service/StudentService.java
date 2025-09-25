package com.studentmicroservice.Service;

import com.studentmicroservice.Entity.Student;
import java.util.List;

public interface StudentService {
    Student create(Student student);
    Student getById(Long id);
    List<Student> getAll();
    Student update(Long id, Student student);
    void delete(Long id);
}
