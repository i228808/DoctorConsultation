package com.proj.Repository;

import com.proj.Bean.Doctor;
import com.proj.Bean.Patient;
import com.proj.Bean.Prescription;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Integer> {

    // Corrected Query Method and JPQL Query
    @Query("SELECT p FROM Patient p WHERE p.email = :email AND p.password = :password")
    Patient findPatientByEmailAndPassword(String email, String password);

    @Query("SELECT p FROM Patient p WHERE p.email = :email")
    Patient findPatientByEmail(String email);

    // ... other repository methods
    Prescription save(Prescription prescription);

    @Query("SELECT p FROM Patient p Join Appointment a on a.patient_Id=p.id where a.doctor = :doctor")
    List<Patient> findPatientsByDoctor(Doctor doctor);
}
