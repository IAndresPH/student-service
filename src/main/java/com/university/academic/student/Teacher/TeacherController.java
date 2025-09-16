package com.university.academic.student.Teacher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("teacher")
public class TeacherController {
    @Autowired
    private ITeacherService service;

    @GetMapping()
    public List<Teacher> findAll(){
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Teacher> findById(@PathVariable int id){
        return service.findById(id);
    }

    @PostMapping()
    public Teacher save(@RequestBody Teacher teacher){
        return service.save(teacher);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody Teacher teacher, @PathVariable int id){
        service.update(teacher, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id){
        service.delete(id);
    }
}
