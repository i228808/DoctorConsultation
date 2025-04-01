package com.proj.Service;

import com.proj.Bean.Doctor;
import com.proj.Bean.Patient;
import com.proj.Repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Transactional
    public Patient createPatient(Patient patient) {
        if (patientRepository.findPatientByEmail(patient.getEmail()) != null) {
            throw new RuntimeException("Email already exists");
        }
        return patientRepository.save(patient);
    }

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Patient getPatientById(int id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + id));
    }

    @Transactional
    public Patient updatePatient(Patient patient) {
        if (!patientRepository.existsById(patient.getId())) {
            throw new RuntimeException("Patient not found with id: " + patient.getId());
        }
        return patientRepository.save(patient);
    }

    @Transactional(readOnly = true)
    public Patient findPatientByEmailAndPassword(String email, String password) {
        try {
            Patient patient = patientRepository.findPatientByEmailAndPassword(email, password);
            if (patient != null) {
                // Force initialization of collections
                patient.getAppointments().size();
                patient.getMedicalRecords().size();
                patient.getHealthGoals().size();
                patient.getPrescriptions().size();
            }
            return patient;
        } catch (Exception e) {
            throw new RuntimeException("Error during patient authentication", e);
        }
    }

    public void deletePatient(int id) {
        if (!patientRepository.existsById(id)) {
            throw new RuntimeException("Patient not found with id: " + id);
        }
        patientRepository.deleteById(id);
    }

    public boolean findPatientByEmail(String email) {
        return patientRepository.findPatientByEmail(email) != null;
    }

    public List<Patient> getPatientsByDoctor(Doctor doctor) {
        return patientRepository.findPatientsByDoctor(doctor);
    }
}
