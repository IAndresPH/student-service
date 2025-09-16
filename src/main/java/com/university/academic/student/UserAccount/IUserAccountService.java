package com.university.academic.student.UserAccount;


import java.util.List;
import java.util.Optional;

public interface IUserAccountService {
    List<UserAccount> findAll();
    Optional<UserAccount> findById(int id);
    UserAccount save(UserAccount userAccount);
    void update(UserAccount userAccount, int id);
    void delete(int id);
}
