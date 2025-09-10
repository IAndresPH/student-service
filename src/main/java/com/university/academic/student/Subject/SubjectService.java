package com.university.academic.student.Subject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubjectService implements ISubjectService {
    @Autowired
    private SubjectRepository repository;

    @Override
    public List<Subject> findAll(){
        return repository.findAll();
    }

    @Override
    public Optional<Subject> findById(int id){
        return repository.findById(id);
    }

    @Override
    public Subject save(Subject subject){
        return repository.save(subject);
    }

    @Override
    public void update(Subject subject, int id){
        Optional<Subject> personOld = repository.findById(id);
        if(!personOld.isEmpty()){
            Subject subjectUpdate = personOld.get();
            subjectUpdate.setName(subject.getName());
            subjectUpdate.setCredits(subject.getCredits());
            subjectUpdate.setProgramId(subject.getProgramId());
            repository.save(subjectUpdate);
        } else {
            System.out.println("Invalid ID " + id);
        }
    }

    @Override
    public void delete(int id){
        repository.deleteById(id);
    }
}
