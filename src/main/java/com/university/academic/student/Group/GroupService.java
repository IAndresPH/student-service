package com.university.academic.student.Group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupService implements IGroupService {
    @Autowired
    private GroupRepository repository;

    @Override
    public List<Group> findAll(){
        return repository.findAll();
    }

    @Override
    public Optional<Group> findById(int id){
        return repository.findById(id);
    }

    @Override
    public Group save(Group group){
        return repository.save(group);
    }

    @Override
    public void update(Group group, int id){
        Optional<Group> groupOld = repository.findById(id);
        if(!groupOld.isEmpty()){
            Group groupUpdate = groupOld.get();
            groupUpdate.setCode(group.getCode());
            groupUpdate.setSchedule(group.getSchedule());
            groupUpdate.setSubjectId(group.getSubjectId());
            repository.save(groupUpdate);
        } else {
            System.out.println("Invalid ID " + id);
        }
    }

    @Override
    public void delete(int id){
        repository.deleteById(id);
    }
}
