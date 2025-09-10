package com.university.academic.student.Activity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("activity")
public class ActivityController {
    @Autowired
    private IActivityService service;

    @GetMapping()
    public List<Activity> findAll(){
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Activity> findById(@PathVariable int id){
        return service.findById(id);
    }

    @PostMapping()
    public Activity save(@RequestBody Activity activity){
        return service.save(activity);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody Activity activity, @PathVariable int id){
        service.update(activity, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id){
        service.delete(id);
    }
}
