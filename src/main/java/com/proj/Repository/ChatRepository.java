package com.proj.Repository;

import com.proj.Bean.Chat;
import com.proj.Bean.Doctor;
import com.proj.Bean.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Integer> {
    List<Chat> findByDoctorAndPatientOrderByTimestampDesc(Doctor doctor, Patient patient);

    List<Chat> findByDoctorAndIsReadFalseOrderByTimestampDesc(Doctor doctor);

    List<Chat> findByPatientAndIsReadFalseOrderByTimestampDesc(Patient patient);
}