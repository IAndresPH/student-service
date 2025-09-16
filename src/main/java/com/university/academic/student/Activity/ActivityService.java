package com.university.academic.student.Activity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActivityService implements IActivityService {
    @Autowired
    private ActivityRepository repository;

    @Override
    public List<Activity> findAll(){
        return repository.findAll();
    }

    @Override
    public Optional<Activity> findById(int id){
        return repository.findById(id);
    }

    @Override
    public Activity save(Activity activity){
        return repository.save(activity);
    }

    @Override
    public void update(Activity activity, int id){
        Optional<Activity> activityOld = repository.findById(id);
        if(!activityOld.isEmpty()){
            Activity activityUpdate = activityOld.get();
            activityUpdate.setName(activity.getName());
            activityUpdate.setPercentage(activity.getPercentage());
            activityUpdate.setSubjectId(activity.getSubjectId());
            repository.save(activityUpdate);
        } else {
            System.out.println("Invalid ID " + id);
        }
    }

    @Override
    public void delete(int id){
        repository.deleteById(id);
    }
}
