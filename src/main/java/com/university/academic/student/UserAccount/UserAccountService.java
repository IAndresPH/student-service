package com.university.academic.student.UserAccount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserAccountService implements IUserAccountService {
    @Autowired
    private UserAccountRepository repository;

    @Override
    public List<UserAccount> findAll(){
        return repository.findAll();
    }

    @Override
    public Optional<UserAccount> findById(int id){
        return repository.findById(id);
    }

    @Override
    public UserAccount save(UserAccount userAccount){
        return repository.save(userAccount);
    }

    @Override
    public void update(UserAccount userAccount, int id){
        Optional<UserAccount> personOld = repository.findById(id);
        if(!personOld.isEmpty()){
            UserAccount userAccountUpdate = personOld.get();
            userAccountUpdate.setUsername(userAccount.getUsername());
            userAccountUpdate.setEmail(userAccount.getEmail());
            userAccountUpdate.setPassword(userAccount.getPassword());
            userAccountUpdate.setPersonId(userAccount.getPersonId());
            userAccountUpdate.setRoleId(userAccount.getRoleId());
            repository.save(userAccountUpdate);
        } else {
            System.out.println("Invalid ID " + id);
        }
    }

    @Override
    public void delete(int id){
        repository.deleteById(id);
    }
}
