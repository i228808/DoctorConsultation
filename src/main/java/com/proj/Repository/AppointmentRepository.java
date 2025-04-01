package com.proj.Repository;

import com.proj.Bean.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    static List<Appointment> getAppointmentByDoctorIdAndDate(int id) {
        return null;
    }

    @Query("SELECT a FROM Appointment a WHERE a.apptTime = :apptTime AND a.apptDate = :apptDate AND a.doctor.id = :doctorId")
    Appointment findAppointmentByApptTimeAndApptDateAndDoctorId(@Param("apptTime") LocalTime apptTime,
                                                                @Param("apptDate") LocalDate apptDate,
                                                                @Param("doctorId") int doctorId);

    @Override
    Appointment save(Appointment appointment);

    @Transactional
    @Modifying
    @Query("DELETE FROM Appointment a WHERE a.apptId = :appointmentId")

    void deleteAppointmentById(@Param("appointmentId") int appointmentId);

    @Query("SELECT a FROM Appointment a WHERE a.doctor.id = :doctorId AND a.apptDate = :apptDate")
    List<Appointment> findAppointmentByDoctorIdAndDate(@Param("doctorId") int doctorId, @Param("apptDate") LocalDate apptDate);
}