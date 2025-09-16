package com.university.academic.student.Program;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("program")
public class ProgramController {
    @Autowired
    private IProgramService service;

    @GetMapping()
    public List<Program> findAll(){
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Program> findById(@PathVariable int id){
        return service.findById(id);
    }

    @PostMapping()
    public Program save(@RequestBody Program program){
        return service.save(program);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody Program program, @PathVariable int id){
        service.update(program, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id){
        service.delete(id);
    }
}
