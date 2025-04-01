package com.proj.Service;

import com.proj.Bean.Doctor;
import com.proj.Bean.Rating;
import com.proj.Repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.proj.Bean.Patient;
import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {
    @Autowired
    private DoctorRepository doctorRepository;

    // Create
    public Doctor addDoctor(Doctor doctor) {
        // Note: Validation (e.g., email uniqueness, required fields) is skipped here
        return doctorRepository.save(doctor);
    }

    // Read
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    public List<Patient> getDoctorPatients(int doctorId) {
        return doctorRepository.findPatientsByDoctorId(doctorId);
    }

    public Optional<Doctor> getDoctorById(int id) {
        if (doctorRepository.existsById(id)) {
            return doctorRepository.findById(id);
        } else {
            return null;
        }
    }

    // Delete
    public void deleteDoctor(int id) {
        if (!doctorRepository.existsById(id))

            doctorRepository.deleteById(id);
    }

    // Additional Methods

    // Example: Get doctors by specialization
    public List<Doctor> getDoctorsBySpecialization(String specialization) {
        return doctorRepository.findBySpecialization(specialization);
    }

    // Example: Get doctors by name (you already have a similar query in your
    // repository)
    public List<Doctor> findDoctorsByName(String firstName, String lastName) {
        return doctorRepository.findDoctorsByName(firstName, lastName);
    }

    // Example: Get average rating for a doctor (you already have a similar query in
    // your repository)
    public Double getAverageRatingForDoctor(int doctorId) {
        return doctorRepository.findAverageRatingByDoctorId(doctorId);
    }

    public List<Rating> getAllRatingsForDoctor(int doctorId) {
        return doctorRepository.findAllRatingsByDoctorId(doctorId);
    }

    public Doctor findDoctorByEmailAndPassword(String email, String password) {
        return doctorRepository.findDoctorByEmailAndPassword(email, password);
    }

    public boolean findDoctorByEmail(String email) {
        return doctorRepository.findDoctorByEmail(email) != null;
    }

    public List<Doctor> searchDoctors(String name, String specialization) {
        if (name != null && specialization != null) {
            return doctorRepository.findDoctorsByNameAndSpecialization(name, specialization);
        } else if (name != null) {
            return doctorRepository.findDoctorsByName(name, null);
        } else if (specialization != null) {
            return doctorRepository.findDoctorsBySpecialization(specialization);
        } else {
            return doctorRepository.findAll();
        }
    }

    public Object getAverageRating(int id) {
        return doctorRepository.findAverageRatingByDoctorId(id);
    }

    public Doctor updateDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }
}
