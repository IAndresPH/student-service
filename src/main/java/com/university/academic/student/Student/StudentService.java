package com.university.academic.student.Student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService implements IStudentService {
    @Autowired
    private StudentRepository repository;

    @Override
    public List<Student> findAll(){
        return repository.findAll();
    }

    @Override
    public Optional<Student> findById(int id){
        return repository.findById(id);
    }

    @Override
    public Student save(Student student){
        return repository.save(student);
    }

    @Override
    public void update(Student student, int id){
        Optional<Student> studentOld = repository.findById(id);
        if(!studentOld.isEmpty()){
            Student studentUpdate = studentOld.get();
            studentUpdate.setCode(student.getCode());
            studentUpdate.setSemester(student.getSemester());
            studentUpdate.setCareer(student.getCareer());
            studentUpdate.setGpa(student.getGpa());
            studentUpdate.setPersonId(student.getPersonId());
            repository.save(studentUpdate);
        } else {
            System.out.println("Invalid ID " + id);
        }
    }

    @Override
    public void delete(int id){
        repository.deleteById(id);
    }
}
