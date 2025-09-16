package com.university.academic.student.Subject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("subject")
public class SubjectController {
    @Autowired
    private ISubjectService service;

    @GetMapping()
    public List<Subject> findAll(){
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Subject> findById(@PathVariable int id){
        return service.findById(id);
    }

    @PostMapping()
    public Subject save(@RequestBody Subject subject){
        return service.save(subject);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody Subject subject, @PathVariable int id){
        service.update(subject, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id){
        service.delete(id);
    }
}
