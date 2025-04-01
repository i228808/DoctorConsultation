package com.proj.Service;

import com.proj.Bean.Prescription;
import com.proj.Repository.prescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PrescriptionService {
    private final prescriptionRepository prescriptionRepository;

    @Autowired
    public PrescriptionService(prescriptionRepository prescriptionRepository) {
        this.prescriptionRepository = prescriptionRepository;
    }

    public Prescription createPrescription(Prescription prescription) {
        return prescriptionRepository.save(prescription);
    }

    public List<Prescription> getAllPrescriptions() {
        return prescriptionRepository.findAll();
    }

    public Optional<Prescription> getPrescriptionById(int id) {
        return prescriptionRepository.findById(id);
    }
    public List<Prescription> getPrescriptionsByPatientId(int patientId) {
        return prescriptionRepository.findByPatientId(patientId);
    }

    public void deletePrescriptionById(int id) {
        prescriptionRepository.deleteById(id);
    }
}
