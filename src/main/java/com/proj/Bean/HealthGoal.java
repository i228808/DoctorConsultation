package com.proj.Bean;

import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "HealthGoals")
public class HealthGoal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @Column(name = "goal_description")
    private String goalDescription;

    @Column(name = "target_value")
    private int targetValue;

    @Column(name = "status")
    private String status;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "updated_at")
    private LocalDate updatedAt;

    @Column(name="progress")
    private int progress;

    public HealthGoal() {
    }

    public HealthGoal(Doctor doctor, Patient patient, String goalDescription, int targetValue, String status, LocalDate createdAt, LocalDate updatedAt) {
        this.doctor = doctor;
        this.patient = patient;
        this.goalDescription = goalDescription;
        this.targetValue = targetValue;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.progress = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public String getGoalDescription() {
        return goalDescription;
    }

    public void setGoalDescription(String goalDescription) {
        this.goalDescription = goalDescription;
    }

    public int getTargetValue() {
        return targetValue;
    }

    public void setTargetValue(int targetValue) {
        this.targetValue = targetValue;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    @Override
    public String toString() {
        return "HealthGoal{" +
                "id=" + id +
                ", doctor=" + doctor +
                ", patient=" + patient +
                ", goalDescription='" + goalDescription + '\'' +
                ", targetValue=" + targetValue +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", progress=" + progress +
                '}';
    }

    // Implement equals and hashCode if necessary
}
