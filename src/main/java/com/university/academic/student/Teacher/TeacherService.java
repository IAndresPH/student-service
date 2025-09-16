package com.university.academic.student.Teacher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherService implements ITeacherService {
    @Autowired
    private TeacherRepository repository;

    @Override
    public List<Teacher> findAll(){
        return repository.findAll();
    }

    @Override
    public Optional<Teacher> findById(int id){
        return repository.findById(id);
    }

    @Override
    public Teacher save(Teacher teacher){
        return repository.save(teacher);
    }

    @Override
    public void update(Teacher teacher, int id){
        Optional<Teacher> teacherOld = repository.findById(id);
        if(!teacherOld.isEmpty()){
            Teacher teacherUpdate = teacherOld.get();
            teacherUpdate.setSpecialty(teacher.getSpecialty());
            teacherUpdate.setPersonId(teacher.getPersonId());
            repository.save(teacherUpdate);
        } else {
            System.out.println("Invalid ID " + id);
        }
    }

    @Override
    public void delete(int id){
        repository.deleteById(id);
    }
}
