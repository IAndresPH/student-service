package com.university.academic.student.Role;


import java.util.List;
import java.util.Optional;

public interface IRoleService {
    List<Role> findAll();
    Optional<Role> findById(int id);
    Role save(Role role);
    void update(Role role, int id);
    void delete(int id);
}
