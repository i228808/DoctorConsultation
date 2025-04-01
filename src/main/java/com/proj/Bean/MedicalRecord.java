package com.proj.Bean;

import java.time.LocalDate;


import jakarta.persistence.*;

@Entity
@Table(name = "MedicalRecord")
public class MedicalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int recordID;

    private String descript;
    private LocalDate recordDate;

    @ManyToOne
    @JoinColumn(name = "PatientID")
    private Patient patient;

    // Constructors, Getters, and Setters
    // Constructors
    public MedicalRecord() {
    }

    public MedicalRecord(String descript, LocalDate recordDate, Patient patient) {
        this.descript = descript;
        this.recordDate = recordDate;
        this.patient = patient;
    }

    // Getters and Setters
    public int getRecordID() {
        return recordID;
    }

    public void setRecordID(int recordID) {
        this.recordID = recordID;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public LocalDate getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(LocalDate recordDate) {
        this.recordDate = recordDate;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
