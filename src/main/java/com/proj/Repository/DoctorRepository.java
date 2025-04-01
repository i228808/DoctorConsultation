package com.proj.Repository;

import com.proj.Bean.Doctor;
import com.proj.Bean.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.proj.Bean.Patient;
import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {

    @Query("SELECT d FROM Doctor d WHERE d.firstName = :firstName AND d.lastName = :lastName") // Corrected query
    List<Doctor> findDoctorsByName(String firstName, String lastName);

    @Query("SELECT AVG(r.stars) FROM Rating r WHERE r.doctor.id = :doctorId")
    Double findAverageRatingByDoctorId(int doctorId);

    @Query("SELECT r FROM Rating r WHERE r.doctor.id = :doctorId")
    List<Rating> findAllRatingsByDoctorId(int doctorId);

    @Query("SELECT d FROM Doctor d WHERE d.specialization = :specialization")
    List<Doctor> findBySpecialization(String specialization);

    @Query("select patient p from Appointment a where a.doctor.id = :doctorId")
    List<Patient> findPatientsByDoctorId(int doctorId);

    @Query("SELECT d FROM Doctor d WHERE d.email = :email AND d.password = :password")
    Doctor findDoctorByEmailAndPassword(String email, String password);

    @Query("SELECT d FROM Doctor d WHERE d.email = :email")
    Doctor findDoctorByEmail(String email);

    @Query("SELECT d FROM Doctor d WHERE d.specialization = :specialization")
    List<Doctor> findDoctorsBySpecialization(String specialization);

    @Query("SELECT d FROM Doctor d WHERE d.firstName = :name OR d.specialization = :specialization")
    List<Doctor> findDoctorsByNameAndSpecialization(String name, String specialization);
}
