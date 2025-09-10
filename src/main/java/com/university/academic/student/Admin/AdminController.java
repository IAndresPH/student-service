package com.university.academic.student.Admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("admin")
public class AdminController {
    @Autowired
    private IAdminService service;

    @GetMapping()
    public List<Admin> findAll(){
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Admin> findById(@PathVariable int id){
        return service.findById(id);
    }

    @PostMapping()
    public Admin save(@RequestBody Admin admin){
        return service.save(admin);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody Admin admin, @PathVariable int id){
        service.update(admin, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id){
        service.delete(id);
    }
}
