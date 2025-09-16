package com.university.academic.student.Student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("student")
public class StudentController {
    @Autowired
    private IStudentService service;

    @GetMapping()
    public List<Student> findAll(){
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Student> findById(@PathVariable int id){
        return service.findById(id);
    }

    @PostMapping()
    public Student save(@RequestBody Student student){
        return service.save(student);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody Student student, @PathVariable int id){
        service.update(student, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id){
        service.delete(id);
    }
}
