package com.university.academic.student.Person;

import com.university.academic.student.Person.Person;
import com.university.academic.student.Person.IPersonService;
import com.university.academic.student.Person.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PersonService implements IPersonService {
    @Autowired
    private PersonRepository repository;

    @Override
    public List<Person> findAll(){
        return repository.findAll();
    }

    @Override
    public Optional<Person> findById(int id){
        return repository.findById(id);
    }

    @Override
    public Person save(Person person){
        return repository.save(person);
    }

    @Override
    public void update(Person person, int id){
        Optional<Person> personOld = repository.findById(id);
        if(!personOld.isEmpty()){
            Person personUpdate = personOld.get();
            personUpdate.setName(person.getName());
            personUpdate.setSurname(person.getSurname());
            personUpdate.setDocument(person.getDocument());
            personUpdate.setPhone(person.getPhone());
            repository.save(personUpdate);
        } else {
            System.out.println("Invalid ID " + id);
        }
    }

    @Override
    public void delete(int id){
        repository.deleteById(id);
    }
}
