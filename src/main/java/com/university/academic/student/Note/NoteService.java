package com.university.academic.student.Note;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteService implements INoteService {
    @Autowired
    private NoteRepository repository;

    @Override
    public List<Note> findAll(){
        return repository.findAll();
    }

    @Override
    public Optional<Note> findById(int id){
        return repository.findById(id);
    }

    @Override
    public Note save(Note note){
        return repository.save(note);
    }

    @Override
    public void update(Note note, int id){
        Optional<Note> noteOld = repository.findById(id);
        if(!noteOld.isEmpty()){
            Note noteUpdate = noteOld.get();
            noteUpdate.setValue(note.getValue());
            noteUpdate.setObservation(note.getObservation());
            noteUpdate.setActivityId(note.getActivityId());
            repository.save(noteUpdate);
        } else {
            System.out.println("Invalid ID " + id);
        }
    }

    @Override
    public void delete(int id){
        repository.deleteById(id);
    }
}
