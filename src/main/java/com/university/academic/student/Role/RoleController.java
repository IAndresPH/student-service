package com.university.academic.student.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("role")
public class RoleController {
    @Autowired
    private IRoleService service;

    @GetMapping()
    public List<Role> findAll(){
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Role> findById(@PathVariable int id){
        return service.findById(id);
    }

    @PostMapping()
    public Role save(@RequestBody Role role){
        return service.save(role);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody Role role, @PathVariable int id){
        service.update(role, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id){
        service.delete(id);
    }
}
