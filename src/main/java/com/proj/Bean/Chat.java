package com.proj.Bean;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Chat")
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int chatId;

    @ManyToOne
    @JoinColumn(name = "doctorId")
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "patientId")
    private Patient patient;

    @Column(columnDefinition = "TEXT")
    private String message;

    private LocalDateTime timestamp;

    private boolean isRead;

    @Column(length = 50)
    private String senderType; // "DOCTOR" or "PATIENT"

    public Chat() {
        this.timestamp = LocalDateTime.now();
        this.isRead = false;
    }

    // Getters and Setters
    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public String getSenderType() {
        return senderType;
    }

    public void setSenderType(String senderType) {
        this.senderType = senderType;
    }
}