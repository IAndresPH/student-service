package com.university.academic.student.University;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("university")
public class UniversityController {
    @Autowired
    private IUniversityService service;

    @GetMapping()
    public List<University> findAll(){
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Optional<University> findById(@PathVariable int id){
        return service.findById(id);
    }

    @PostMapping()
    public University save(@RequestBody University university){
        return service.save(university);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody University university, @PathVariable int id){
        service.update(university, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id){
        service.delete(id);
    }
}
