package com.university.academic.student.Person;


import com.university.academic.student.Person.Person;

import java.util.List;
import java.util.Optional;

public interface IPersonService {
    List<Person> findAll();
    Optional<Person> findById(int id);
    Person save(Person person);
    void update(Person person, int id);
    void delete(int id);
}
