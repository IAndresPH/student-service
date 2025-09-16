package com.university.academic.student.Program;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProgramService implements IProgramService {
    @Autowired
    private ProgramRepository repository;

    @Override
    public List<Program> findAll(){
        return repository.findAll();
    }

    @Override
    public Optional<Program> findById(int id){
        return repository.findById(id);
    }

    @Override
    public Program save(Program program){
        return repository.save(program);
    }

    @Override
    public void update(Program program, int id){
        Optional<Program> programOld = repository.findById(id);
        if(!programOld.isEmpty()){
            Program programUpdate = programOld.get();
            programUpdate.setName(program.getName());
            programUpdate.setFaculty(program.getFaculty());
            programUpdate.setUniversityId(program.getUniversityId());
            repository.save(programUpdate);
        } else {
            System.out.println("Invalid ID " + id);
        }
    }

    @Override
    public void delete(int id){
        repository.deleteById(id);
    }
}
