package com.university.academic.student.Note;


import java.util.List;
import java.util.Optional;

public interface INoteService {
    List<Note> findAll();
    Optional<Note> findById(int id);
    Note save(Note note);
    void update(Note note, int id);
    void delete(int id);
}
