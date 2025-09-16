package com.university.academic.student.Admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService implements IAdminService {
    @Autowired
    private AdminRepository repository;

    @Override
    public List<Admin> findAll(){
        return repository.findAll();
    }

    @Override
    public Optional<Admin> findById(int id){
        return repository.findById(id);
    }

    @Override
    public Admin save(Admin admin){
        return repository.save(admin);
    }

    @Override
    public void update(Admin admin, int id){
        Optional<Admin> adminOld = repository.findById(id);
        if(!adminOld.isEmpty()){
            Admin adminUpdate = adminOld.get();
            adminUpdate.setAccessLevel(admin.getAccessLevel());
            adminUpdate.setPersonId(admin.getPersonId());
            repository.save(adminUpdate);
        } else {
            System.out.println("Invalid ID " + id);
        }
    }

    @Override
    public void delete(int id){
        repository.deleteById(id);
    }
}
