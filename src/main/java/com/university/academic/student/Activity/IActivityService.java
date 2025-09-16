package com.university.academic.student.Activity;


import java.util.List;
import java.util.Optional;

public interface IActivityService {
    List<Activity> findAll();
    Optional<Activity> findById(int id);
    Activity save(Activity activity);
    void update(Activity activity, int id);
    void delete(int id);
}
