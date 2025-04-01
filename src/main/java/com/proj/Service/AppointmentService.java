package com.proj.Service;

import com.proj.Bean.Appointment;
import com.proj.Bean.AppointmentId;
import com.proj.Repository.AppointmentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public  List<Appointment> getAppointmentsByDoctorIdAndDate(int id) {

        LocalDate date = LocalDate.now();
        return appointmentRepository.findAppointmentByDoctorIdAndDate(id, date);

    }

    // Create
    public Appointment createAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }
    public Appointment getAppointmentByApptTimeAndApptDateAndDoctorId(LocalTime apptTime, LocalDate apptDate, int doctorId) {
        return appointmentRepository.findAppointmentByApptTimeAndApptDateAndDoctorId(apptTime, apptDate, doctorId);
    }
    // Read
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public Optional<Appointment> getAppointmentById(int appointmentId) {
        return appointmentRepository.findById(appointmentId);
    }
    @Transactional
    public void deleteAppointmentById(int appointmentId) {
        appointmentRepository.deleteAppointmentById(appointmentId);
    }
    public void updateAppointment(Appointment appointment) {

        appointmentRepository.save(appointment);

    }
	public void rescheduleAppointment(int appointmentId, LocalDate localDate, LocalTime localTime) {
		// TODO Auto-generated method stub
		
	}

	
}