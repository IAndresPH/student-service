package com.university.academic.student.University;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UniversityService implements IUniversityService {
    @Autowired
    private UniversityRepository repository;

    @Override
    public List<University> findAll(){
        return repository.findAll();
    }

    @Override
    public Optional<University> findById(int id){
        return repository.findById(id);
    }

    @Override
    public University save(University university){
        return repository.save(university);
    }

    @Override
    public void update(University university, int id){
        Optional<University> universityOld = repository.findById(id);
        if(!universityOld.isEmpty()){
            University universityUpdate = universityOld.get();
            universityUpdate.setName(university.getName());
            universityUpdate.setDirection(university.getDirection());
            repository.save(universityUpdate);
        } else {
            System.out.println("Invalid ID " + id);
        }
    }

    @Override
    public void delete(int id){
        repository.deleteById(id);
    }
}
