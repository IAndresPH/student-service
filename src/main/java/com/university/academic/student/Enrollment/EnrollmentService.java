package com.university.academic.student.Enrollment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnrollmentService implements IEnrollmentService {
    @Autowired
    private EnrollmentRepository repository;

    @Override
    public List<Enrollment> findAll(){
        return repository.findAll();
    }

    @Override
    public Optional<Enrollment> findById(int id){
        return repository.findById(id);
    }

    @Override
    public Enrollment save(Enrollment enrollment){
        return repository.save(enrollment);
    }

    @Override
    public void update(Enrollment enrollment, int id){
        Optional<Enrollment> enrollmentOld = repository.findById(id);
        if(!enrollmentOld.isEmpty()){
            Enrollment enrollmentUpdate = enrollmentOld.get();
            enrollmentUpdate.setPeriod(enrollment.getPeriod());
            enrollmentUpdate.setDate(enrollment.getDate());
            enrollmentUpdate.setStudentId(enrollment.getStudentId());
            enrollmentUpdate.setSubjectId(enrollment.getSubjectId());
            repository.save(enrollmentUpdate);
        } else {
            System.out.println("Invalid ID " + id);
        }
    }

    @Override
    public void delete(int id){
        repository.deleteById(id);
    }
}
