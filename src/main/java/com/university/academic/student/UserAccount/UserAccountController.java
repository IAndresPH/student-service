package com.university.academic.student.UserAccount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("user_account")
public class UserAccountController {
    @Autowired
    private IUserAccountService service;

    @GetMapping()
    public List<UserAccount> findAll(){
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Optional<UserAccount> findById(@PathVariable int id){
        return service.findById(id);
    }

    @PostMapping()
    public UserAccount save(@RequestBody UserAccount userAccount){
        return service.save(userAccount);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody UserAccount userAccount, @PathVariable int id){
        service.update(userAccount, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id){
        service.delete(id);
    }
}
