package com.university.academic.student.Enrollment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("enrollment")
public class EnrollmentController {
    @Autowired
    private IEnrollmentService service;

    @GetMapping()
    public List<Enrollment> findAll(){
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Enrollment> findById(@PathVariable int id){
        return service.findById(id);
    }

    @PostMapping()
    public Enrollment save(@RequestBody Enrollment enrollment){
        return service.save(enrollment);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody Enrollment enrollment, @PathVariable int id){
        service.update(enrollment, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id){
        service.delete(id);
    }
}
