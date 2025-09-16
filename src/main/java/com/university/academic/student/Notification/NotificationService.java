package com.university.academic.student.Notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationService implements INotificationService {
    @Autowired
    private NotificationRepository repository;

    @Override
    public List<Notification> findAll(){
        return repository.findAll();
    }

    @Override
    public Optional<Notification> findById(int id){
        return repository.findById(id);
    }

    @Override
    public Notification save(Notification notification){
        return repository.save(notification);
    }

    @Override
    public void update(Notification notification, int id){
        Optional<Notification> notificationOld = repository.findById(id);
        if(!notificationOld.isEmpty()){
            Notification notificationUpdate = notificationOld.get();
            notificationUpdate.setMessage(notification.getMessage());
            notificationUpdate.setDate(notification.getDate());
            notificationUpdate.setPersonId(notification.getPersonId());
            repository.save(notificationUpdate);
        } else {
            System.out.println("Invalid ID " + id);
        }
    }

    @Override
    public void delete(int id){
        repository.deleteById(id);
    }
}
