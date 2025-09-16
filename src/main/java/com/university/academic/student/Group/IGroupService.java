package com.university.academic.student.Group;


import java.util.List;
import java.util.Optional;

public interface IGroupService {
    List<Group> findAll();
    Optional<Group> findById(int id);
    Group save(Group group);
    void update(Group group, int id);
    void delete(int id);
}
