package com.estudentmicroservice.Service.imp;

import com.estudentmicroservice.Entity.Student;
import com.estudentmicroservice.Repository.StudentRepository;
import com.estudentmicroservice.Service.StudentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository repository;

    public StudentServiceImpl(StudentRepository repository) {
        this.repository = repository;
    }

    @Override
    public Student create(Student student) {
        return repository.save(student);
    }

    @Override
    public Student getById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    @Override
    public List<Student> getAll() {
        return repository.findAll();
    }

    @Override
    public Student update(Long id, Student student) {
        Student existing = repository.findById(id).orElseThrow();
        existing.setCode(student.getCode());
        existing.setSemester(student.getSemester());
        existing.setCareer(student.getCareer());
        existing.setGpa(student.getGpa());
        return repository.save(existing);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
