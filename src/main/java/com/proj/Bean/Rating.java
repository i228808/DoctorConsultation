package com.proj.Bean;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Rating")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int ratingId;
    private int docId;

    private int patientId;

    @ManyToOne
    @JoinColumn(name = "docId", insertable = false, updatable = false) // Part of the composite key
    private Doctor doctor;

    public Rating(int id, int docId, int patientId, int stars, String descript) {
        this.ratingId = id;
        this.docId = docId;
        this.patientId = patientId;
        this.stars = stars;
        this.descript = descript;
        this.date = LocalDate.now();
    }

    @ManyToOne
    @JoinColumn(name = "patientId", insertable = false, updatable = false) // Part of the composite key
    private Patient patient;

    private int stars;
    private String descript;
    private LocalDate date;

    // Constructors
    public Rating() {
        this.date = LocalDate.now();
    }

    // Getters and Setters

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

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public int getDocId() {
        return docId;
    }

    public void setDocId(int docId) {
        this.docId = docId;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}

// Create a separate class for the composite primary key
