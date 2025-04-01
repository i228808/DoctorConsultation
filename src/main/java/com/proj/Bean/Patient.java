package com.proj.Bean;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Patient")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "FirstName")
    private String firstName;

    @Column(name = "LastName")
    private String lastName;

    @Column(name = "ProfilePicture")
    private String profilePicture;

    @Column(name = "gender")
    private String gender;

    @Column(name = "email", unique = true) // Ensure email uniqueness
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "phone")
    private String phone;

    @Column(name = "dateOfBirth")
    private LocalDate dateOfBirth;

    @Column(name = "address")
    private String address;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Appointment> appointments = new ArrayList<>();

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<MedicalRecord> medicalRecords = new ArrayList<>();

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<HealthGoal> healthGoals = new ArrayList<>();

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Prescription> prescriptions = new ArrayList<>();

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void SetHealthGoals(List<HealthGoal> healthGoals) {
        this.healthGoals = healthGoals;
    }

    public void AddHealthGoal(HealthGoal healthGoal) {
        this.healthGoals.add(healthGoal);
    }

    // Constructors, Getters, and Setters
    // Constructors
    public Patient() {
        this.appointments = new ArrayList<>();
        this.medicalRecords = new ArrayList<>();
        this.healthGoals = new ArrayList<>();
        this.prescriptions = new ArrayList<>();
    }

    public Patient(String firstName, String lastName, String email, String password, String phone,
            LocalDate dateOfBirth, String address, String gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.gender = gender;
        this.appointments = new ArrayList<>();
        this.medicalRecords = new ArrayList<>();
        this.healthGoals = new ArrayList<>();
        this.prescriptions = new ArrayList<>();
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public List<MedicalRecord> getMedicalRecords() {
        return medicalRecords;
    }

    public void setMedicalRecords(List<MedicalRecord> medicalRecords) {
        this.medicalRecords = medicalRecords;
    }

    public List<HealthGoal> getHealthGoals() {
        return healthGoals;
    }

    public void setHealthGoals(List<HealthGoal> healthGoals) {
        this.healthGoals = healthGoals;
    }

    public List<Prescription> getPrescriptions() {
        return prescriptions;
    }

    public void setPrescriptions(List<Prescription> prescriptions) {
        this.prescriptions = prescriptions;
    }
}
