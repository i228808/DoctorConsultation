package com.proj.Bean;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Prescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    private String medicationDetails;
    private LocalDate dateIssued;
    private String dosage;
    private LocalDate dateUntil;
    private String instructions;

    // Constructors, getters, and setters
    public Prescription() {}

    public Prescription(Doctor doctor, Patient patient, String medicationDetails, LocalDate dateIssued) {
        this.doctor = doctor;
        this.patient = patient;
        this.medicationDetails = medicationDetails;
        this.dateIssued = dateIssued;
    }

    public Prescription(Doctor doctor, Patient patient, String medicationDetails, LocalDate dateIssued, String dosage, LocalDate dateUntil, String instructions) {
        this.doctor = doctor;
        this.patient = patient;
        this.medicationDetails = medicationDetails;
        this.dateIssued = dateIssued;
        this.dosage = dosage;
        this.dateUntil = dateUntil;
        this.instructions = instructions;
    }
    public String getDosage() {
        return dosage;
    }
    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public LocalDate getDateUntil() {
        return dateUntil;
    }

    public void setDateUntil(LocalDate dateUntil) {
        this.dateUntil = dateUntil;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public String getMedicationDetails() {
        return medicationDetails;
    }

    public void setMedicationDetails(String medicationDetails) {
        this.medicationDetails = medicationDetails;
    }

    public LocalDate getDateIssued() {
        return dateIssued;
    }

    public void setDateIssued(LocalDate dateIssued) {
        this.dateIssued = dateIssued;
    }
}
