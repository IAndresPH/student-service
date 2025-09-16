package com.university.academic.student.Note;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("note")
public class NoteController {
    @Autowired
    private INoteService service;

    @GetMapping()
    public List<Note> findAll(){
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Note> findById(@PathVariable int id){
        return service.findById(id);
    }

    @PostMapping()
    public Note save(@RequestBody Note note){
        return service.save(note);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody Note note, @PathVariable int id){
        service.update(note, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id){
        service.delete(id);
    }
}
