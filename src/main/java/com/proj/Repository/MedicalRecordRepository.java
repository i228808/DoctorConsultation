package com.proj.Repository;

import com.proj.Bean.MedicalRecord;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Integer> {
    
    @Query("SELECT m FROM MedicalRecord m WHERE m.patient.id = :patientId")
    List<MedicalRecord> findByPatientId(int patientId);
    // List<MedicalRecord> findByPatientIdAndDate(int patientId, Date date);
}
