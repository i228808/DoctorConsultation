package com.proj.Repository;

import com.proj.Bean.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    List<Notification> findByDoctorIdAndIsReadFalseOrderByTimestampDesc(Integer doctorId);

    List<Notification> findByPatientIdAndIsReadFalseOrderByTimestampDesc(Integer patientId);

    List<Notification> findByDoctorIdOrderByTimestampDesc(Integer doctorId);

    List<Notification> findByPatientIdOrderByTimestampDesc(Integer patientId);
}