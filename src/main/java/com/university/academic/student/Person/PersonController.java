package com.university.academic.student.Person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("person")
public class PersonController {
    @Autowired
    private IPersonService service;

    @GetMapping()
    public List<Person> findAll(){
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Person> findById(@PathVariable int id){
        return service.findById(id);
    }

    @PostMapping()
    public Person save(@RequestBody Person person){
        return service.save(person);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody Person person, @PathVariable int id){
        service.update(person, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id){
        service.delete(id);
    }
}
