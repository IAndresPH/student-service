package com.estudentmicroservice.Service;

import com.estudentmicroservice.Entity.Student;
import java.util.List;

public interface StudentService {
    Student create(Student student);
    Student getById(Long id);
    List<Student> getAll();
    Student update(Long id, Student student);
    void delete(Long id);
}
