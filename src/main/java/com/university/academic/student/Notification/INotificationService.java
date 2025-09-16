package com.university.academic.student.Notification;


import java.util.List;
import java.util.Optional;

public interface INotificationService {
    List<Notification> findAll();
    Optional<Notification> findById(int id);
    Notification save(Notification notification);
    void update(Notification notification, int id);
    void delete(int id);
}
