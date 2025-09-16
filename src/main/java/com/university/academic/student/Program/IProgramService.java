package com.university.academic.student.Program;


import java.util.List;
import java.util.Optional;

public interface IProgramService {
    List<Program> findAll();
    Optional<Program> findById(int id);
    Program save(Program program);
    void update(Program program, int id);
    void delete(int id);
}
