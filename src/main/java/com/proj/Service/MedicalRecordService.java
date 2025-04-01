package com.proj.Service;

import com.proj.Bean.MedicalRecord;
import com.proj.Repository.MedicalRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicalRecordService {

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    // Create
    public MedicalRecord createMedicalRecord( MedicalRecord medicalRecord) {
        return medicalRecordRepository.save(medicalRecord);
    }

    // Read
    public List<MedicalRecord> getAllMedicalRecords() {
        return medicalRecordRepository.findAll();
    }

    public Optional<MedicalRecord> getMedicalRecordById(int id) {
        return medicalRecordRepository.findById(id);
    }

    public List<MedicalRecord> getMedicalRecordsByPatientId(int patientId) {
        return medicalRecordRepository.findByPatientId(patientId);
    }

    // Delete
    public void deleteMedicalRecord(int id) {
        if (!medicalRecordRepository.existsById(id)) {
        }
        medicalRecordRepository.deleteById(id);
    }
}
