package com.proj.Bean;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.*;

@Entity
@Table(name = "Appointment")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int apptId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "doctor_Id", insertable = false, updatable = false)
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "patient_Id", insertable = false, updatable = false)
    private Patient patient;

    private LocalDate apptDate;
    private LocalTime apptTime;
    private LocalTime duration;

    @Column(name = "doctor_Id")
    private int doctor_Id;

    @Column(name = "patient_Id")
    private int patient_Id;

    private boolean canceled;
    private boolean rescheduled;
    private LocalDate rescheduled_date;
    private LocalTime rescheduled_time;

    // Constructors, Getters, and Setters
    public Appointment() {
    }

    public Appointment(LocalDate apptDate, LocalTime apptTime, Doctor doctor, Patient patient) {
        this.apptDate = apptDate;
        this.apptTime = apptTime;
        this.doctor = doctor;
        this.patient = patient;
        if (doctor != null) {
            this.doctor_Id = doctor.getId();
        }
        if (patient != null) {
            this.patient_Id = patient.getId();
        }
    }

    public int getApptId() {
        return apptId;
    }

    public void setApptId(int apptId) {
        this.apptId = apptId;
    }

    public LocalDate getApptDate() {
        return apptDate;
    }

    public void setApptDate(LocalDate apptDate) {
        this.apptDate = apptDate;
    }

    public LocalTime getApptTime() {
        return apptTime;
    }

    public void setApptTime(LocalTime apptTime) {
        this.apptTime = apptTime;
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

    public int getDoctor_Id() {
        return doctor_Id;
    }

    public void setDoctor_Id(int doctor_Id) {
        this.doctor_Id = doctor_Id;
    }

    public int getPatient_Id() {
        return patient_Id;
    }

    public void setPatient_Id(int patient_Id) {
        this.patient_Id = patient_Id;
    }

    public LocalTime getDuration() {
        return duration;
    }

    public void setDuration(LocalTime duration) {
        this.duration = duration;
    }

    public boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }

    public boolean isRescheduled() {
        return rescheduled;
    }

    public void setRescheduled(boolean rescheduled) {
        this.rescheduled = rescheduled;
    }

    public LocalDate getRescheduled_date() {
        return rescheduled_date;
    }

    public void setRescheduled_date(LocalDate rescheduled_date) {
        this.rescheduled_date = rescheduled_date;
    }

    public LocalTime getRescheduled_time() {
        return rescheduled_time;
    }

    public void setRescheduled_time(LocalTime rescheduled_time) {
        this.rescheduled_time = rescheduled_time;
    }

    public void setDoctor(int doctorId) {
        this.doctor_Id = doctorId;
    }

    public void setPatientId(int id) {
        this.patient_Id = id;
    }

}
