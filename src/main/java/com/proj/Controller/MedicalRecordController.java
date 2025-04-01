package com.proj.Controller;

import com.proj.Bean.MedicalRecord;
import com.proj.Service.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medical-records") // Base path for all medical record endpoints
public class MedicalRecordController {

    @Autowired
    private MedicalRecordService medicalRecordService;

    // Create a new medical record
    @PostMapping
    public ResponseEntity<MedicalRecord> createMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        MedicalRecord savedRecord = medicalRecordService.createMedicalRecord(medicalRecord);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRecord);
    }

    // Get all medical records
    @GetMapping
    public List<MedicalRecord> getAllMedicalRecords() {
        return medicalRecordService.getAllMedicalRecords();
    }

    // Get medical record by ID
    @GetMapping("/{id}")
    public ResponseEntity<MedicalRecord> getMedicalRecordById(@PathVariable int id) {
        return medicalRecordService.getMedicalRecordById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get medical records by patient ID
    @GetMapping("/patient/{patientId}") 
    public List<MedicalRecord> getMedicalRecordsByPatientId(@PathVariable int patientId) {
        return medicalRecordService.getMedicalRecordsByPatientId(patientId);
    }


    // Delete a medical record
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedicalRecord(@PathVariable int id) {
        medicalRecordService.deleteMedicalRecord(id);
        return ResponseEntity.noContent().build();
    }
}
