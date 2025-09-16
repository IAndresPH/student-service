package com.university.academic.student.Notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("notification")
public class NotificationController {
    @Autowired
    private INotificationService service;

    @GetMapping()
    public List<Notification> findAll(){
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Notification> findById(@PathVariable int id){
        return service.findById(id);
    }

    @PostMapping()
    public Notification save(@RequestBody Notification notification){
        return service.save(notification);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody Notification notification, @PathVariable int id){
        service.update(notification, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id){
        service.delete(id);
    }
}
