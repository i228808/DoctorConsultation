package com.proj.Bean;

import java.time.LocalDate;

public class Checkout {
    private int id;
    private int patientId;
    private LocalDate checkoutDate;

    // Constructors
    public Checkout() {}

    public Checkout(int patientId, LocalDate checkoutDate) {
        this.patientId = patientId;
        this.checkoutDate = checkoutDate;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public LocalDate getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(LocalDate checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    // toString() method
    @Override
    public String toString() {
        return "Checkout{" +
                "id=" + id +
                ", patientId=" + patientId +
                ", checkoutDate=" + checkoutDate +
                '}';
    }
}
