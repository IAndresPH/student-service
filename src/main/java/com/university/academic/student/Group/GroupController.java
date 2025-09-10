package com.university.academic.student.Group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("group")
public class GroupController {
    @Autowired
    private IGroupService service;

    @GetMapping()
    public List<Group> findAll(){
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Group> findById(@PathVariable int id){
        return service.findById(id);
    }

    @PostMapping()
    public Group save(@RequestBody Group group){
        return service.save(group);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody Group group, @PathVariable int id){
        service.update(group, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id){
        service.delete(id);
    }
}
