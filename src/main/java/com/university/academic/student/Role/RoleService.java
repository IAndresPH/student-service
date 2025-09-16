package com.university.academic.student.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService implements IRoleService {
    @Autowired
    private RoleRepository repository;

    @Override
    public List<Role> findAll(){
        return repository.findAll();
    }

    @Override
    public Optional<Role> findById(int id){
        return repository.findById(id);
    }

    @Override
    public Role save(Role role){
        return repository.save(role);
    }

    @Override
    public void update(Role role, int id){
        Optional<Role> roleOld = repository.findById(id);
        if(!roleOld.isEmpty()){
            Role roleUpdate = roleOld.get();
            roleUpdate.setName(role.getName());
            roleUpdate.setDescription(role.getDescription());
            repository.save(roleUpdate);
        } else {
            System.out.println("Invalid ID " + id);
        }
    }

    @Override
    public void delete(int id){
        repository.deleteById(id);
    }
}
