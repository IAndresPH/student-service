package com.university.academic.student.Admin;


import java.util.List;
import java.util.Optional;

public interface IAdminService {
    List<Admin> findAll();
    Optional<Admin> findById(int id);
    Admin save(Admin admin);
    void update(Admin admin, int id);
    void delete(int id);
}
