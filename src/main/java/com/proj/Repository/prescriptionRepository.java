package com.proj.Repository;

import com.proj.Bean.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface prescriptionRepository extends JpaRepository<Prescription, Integer> {
    @Query("SELECT p FROM Prescription p WHERE p.patient.id = :patientId")
    List<Prescription> findByPatientId(int patientId);

}
