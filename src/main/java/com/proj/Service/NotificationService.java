package com.proj.Service;

import com.proj.Bean.Notification;
import com.proj.Repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    public Notification createNotification(Integer doctorId, Integer patientId, String message, String type,
            String action) {
        Notification notification = new Notification();
        notification.setDoctorId(doctorId);
        notification.setPatientId(patientId);
        notification.setMessage(message);
        notification.setType(type);
        notification.setAction(action);
        return notificationRepository.save(notification);
    }

    public List<Notification> getUnreadNotifications(Integer doctorId, Integer patientId) {
        if (doctorId != null) {
            return notificationRepository.findByDoctorIdAndIsReadFalseOrderByTimestampDesc(doctorId);
        } else if (patientId != null) {
            return notificationRepository.findByPatientIdAndIsReadFalseOrderByTimestampDesc(patientId);
        }
        return null;
    }

    public List<Notification> getAllNotifications(Integer doctorId, Integer patientId) {
        if (doctorId != null) {
            return notificationRepository.findByDoctorIdOrderByTimestampDesc(doctorId);
        } else if (patientId != null) {
            return notificationRepository.findByPatientIdOrderByTimestampDesc(patientId);
        }
        return null;
    }

    public void markAsRead(List<Notification> notifications) {
        notifications.forEach(notification -> notification.setRead(true));
        notificationRepository.saveAll(notifications);
    }
}