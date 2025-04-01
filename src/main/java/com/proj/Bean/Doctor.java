package com.proj.Bean;

import jakarta.persistence.*;

import java.util.List;
import java.time.LocalDate;

@Entity
@Table(name = "Doctor")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "FirstName")
    private String firstName;

    @Column(name = "LastName")
    private String lastName;

    @Column(name = "Specialization")
    private String specialization;

    @Column(name = "Biography")
    private String biography;

    @Column(name = "ProfilePicture")
    private String profilePicture;

    @Column(name = "gender")
    private String gender;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "phone")
    private String phone;

    @Column(name = "dateOfBirth")
    private LocalDate dateOfBirth;

    @Column(name = "address")
    private String address;

    // Relationships
    @OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Rating> ratings;

    @OneToMany(mappedBy = "doctor", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Appointment> appointments;
    @OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Prescription> prescriptions;
    // Constructors, Getters, and Setters

    // Getters
    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getSpecialization() {
        return specialization;
    }

    public String getBiography() {
        return biography;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public String getGender() {
        return gender;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public List<Prescription> getPrescriptions() {
        return prescriptions;
    }
}