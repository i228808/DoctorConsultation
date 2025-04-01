// AppointmentController.java

package com.proj.Controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.proj.Bean.Appointment;
import com.proj.Bean.Doctor;
import com.proj.Bean.Patient;
import com.proj.Service.AppointmentService;
import com.proj.Service.DoctorService;
import com.proj.Service.PatientService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/appointments")
public class AppointmentController {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

    @PostMapping("/create")
    public ResponseEntity<Appointment> createAppointment(@RequestBody Appointment appointment) {
        Appointment savedAppointment = appointmentService.createAppointment(appointment);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAppointment);
    }

    @GetMapping
    public List<Appointment> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }

    @PostMapping("/book-appointment")
    public String bookAppointment(@RequestParam("doctorId") int doctorId,
            @RequestParam String date,
            @RequestParam String time,
            Model model,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        Optional<Doctor> doctor = doctorService.getDoctorById(doctorId);
        Object currentUser = session.getAttribute("currentUser");

        if (currentUser instanceof Patient) {
            Patient patient = (Patient) currentUser;
            try {
                LocalDate appointmentDate = LocalDate.parse(date);
                LocalTime appointmentTime = LocalTime.parse(time);

                Appointment appointment = new Appointment(appointmentDate, appointmentTime, doctor.orElse(null),
                        patient);
                appointment.setDoctor(doctorId);
                appointment.setPatientId(patient.getId());
                appointmentService.createAppointment(appointment);
                patient.getAppointments().add(appointment);
                redirectAttributes.addFlashAttribute("success", "Appointment booked successfully!");
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("error", "Invalid date or time format.");
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "You must be logged in as a patient to book an appointment.");
        }

        return "redirect:/doctors/profile/" + doctorId;
    }

    @PostMapping("/cancel")
    public String cancelAppointment(@RequestParam("appointmentId") int appointmentId,
            @RequestParam("userId") int userId, Model model) {

        appointmentService.deleteAppointmentById(appointmentId);

        Patient patient = patientService.getPatientById(userId);
        patient.getAppointments().removeIf(appointment -> appointment.getApptId() == appointmentId);
        patientService.updatePatient(patient);
        model.addAttribute("Patient", patient);
        model.addAttribute("appointments", patientService.getPatientById(userId).getAppointments());

        List<Doctor> doctorList = new ArrayList<>();
        patient.getAppointments().forEach(appointment -> doctorList.add(appointment.getDoctor()));
        model.addAttribute("doctors", doctorList);

        return "PatientHome";
    }

    @PostMapping("/update")
    public String updateAppointment(@RequestParam("appointmentId") int appointmentId,
            @RequestParam("userId") int userId, @RequestParam("newDate") LocalDate newDate,
            @RequestParam("newTime") LocalTime newTime, Model model, HttpSession session,
            RedirectAttributes redirectAttributes) {

        Object user = session.getAttribute("currentUser");
        if (user instanceof Patient) {
            Patient patient = (Patient) user;

            Doctor doctor = appointmentService.getAppointmentById(appointmentId).get().getDoctor();

            /*
             * bookAppointment(doctor.getId(), newDate.toString(), newTime.toString(),
             * model,session ,redirectAttributes);
             * patient.getAppointments().removeIf(appointment -> appointment.getApptId() ==
             * appointmentId);
             * appointmentService.deleteAppointmentById(appointmentId);
             */
            patient.getAppointments().forEach(appointment -> {
                if (appointment.getApptId() == appointmentId) {
                    appointment.setApptDate(newDate);
                    appointment.setApptTime(newTime);
                }
            });
            patientService.updatePatient(patient);
            model.addAttribute("Patient", patient);
            model.addAttribute("appointments", patientService.getPatientById(userId).getAppointments());

            List<Doctor> doctorList = new ArrayList<>();
            patient.getAppointments().forEach(appointment -> doctorList.add(appointment.getDoctor()));
            model.addAttribute("doctors", doctorList);
        }
        return "redirect:/patients/home";
    }

    @PostMapping("/update/doctors")
    public ModelAndView updateDoctorAppointments(HttpSession session,
            @RequestParam("appointmentId") int appointmentId,
            @RequestParam("newDate") LocalDate newDate,
            @RequestParam("newTime") LocalTime newTime,
            Model model,
            RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView();
        Optional<Appointment> existingAppointmentOpt = appointmentService.getAppointmentById(appointmentId);
        if (!existingAppointmentOpt.isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Appointment not found");
            modelAndView.setViewName("redirect:/doctors/myappointments");
            return modelAndView;
        }

        Appointment existingAppointment = existingAppointmentOpt.get();
        existingAppointment.setDoctor_Id(existingAppointment.getDoctor().getId());
        existingAppointment.setPatient_Id(existingAppointment.getPatient().getId());
        Patient patient = existingAppointment.getPatient();
        Doctor doctor = existingAppointment.getDoctor();

        existingAppointment.setApptDate(newDate);
        existingAppointment.setApptTime(newTime);

        try {
            appointmentService.updateAppointment(existingAppointment);
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("error", "Failed to update the appointment. Please try again.");
            modelAndView.setViewName("redirect:/doctors/myappointments");
            return modelAndView;
        }

        List<Appointment> pastAppointments = new ArrayList<>();
        List<Appointment> upcomingAppointments = new ArrayList<>();
        doctor.getAppointments().forEach(appointment -> {
            LocalDateTime appointmentDateTime = LocalDateTime.of(appointment.getApptDate(), appointment.getApptTime());
            if (appointmentDateTime.isBefore(LocalDateTime.now())) {
                pastAppointments.add(appointment);
            } else {
                upcomingAppointments.add(appointment);
            }
        });

        modelAndView.addObject("patient", patient);
        modelAndView.addObject("pastAppointments", pastAppointments);
        modelAndView.addObject("upcomingAppointments", upcomingAppointments);
        modelAndView.setViewName("myAppointments");
        return modelAndView;
    }

    @PostMapping("/delete")
    public ModelAndView deleteAppointment(@RequestParam("appointmentId") int appointmentId, HttpSession session,
            Model model, RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView();
        Object user = session.getAttribute("currentUser");
        if (user instanceof Doctor) {
            Doctor doctor = (Doctor) user;
            appointmentService.deleteAppointmentById(appointmentId);
            doctor.getAppointments().removeIf(appointment -> appointment.getApptId() == appointmentId);
            List<Appointment> pastAppointments = new ArrayList<>();
            List<Appointment> upcomingAppointments = new ArrayList<>();
            doctor.getAppointments().forEach(appointment -> {
                LocalDateTime appointmentDateTime = LocalDateTime.of(appointment.getApptDate(),
                        appointment.getApptTime());
                if (appointmentDateTime.isBefore(LocalDateTime.now())) {
                    pastAppointments.add(appointment);
                } else {
                    upcomingAppointments.add(appointment);
                }
            });

            modelAndView.addObject("pastAppointments", pastAppointments);
            modelAndView.addObject("upcomingAppointments", upcomingAppointments);
            modelAndView.setViewName("myAppointments");
        }
        return modelAndView;
    }

    @GetMapping("/book/{doctorId}")
    public String showBookingForm(@PathVariable int doctorId, Model model, HttpSession session) {
        try {
            Object currentUser = session.getAttribute("currentUser");
            if (!(currentUser instanceof Patient)) {
                return "redirect:/login";
            }

            Patient patient = (Patient) currentUser;
            Doctor doctor = doctorService.getDoctorById(doctorId).orElse(null);

            if (doctor == null) {
                return "redirect:/doctors/searchpage";
            }

            model.addAttribute("doctor", doctor);
            model.addAttribute("patient", patient);
            model.addAttribute("minDate", LocalDate.now().toString());
            model.addAttribute("appointment", new Appointment());

            return "BookAppointment";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/doctors/searchpage";
        }
    }

    @PostMapping("/book/{doctorId}")
    public String bookAppointment(
            @PathVariable int doctorId,
            @RequestParam("apptDate") LocalDate apptDate,
            @RequestParam("apptTime") LocalTime apptTime,
            @RequestParam("duration") int duration,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        try {
            Object currentUser = session.getAttribute("currentUser");
            if (!(currentUser instanceof Patient)) {
                return "redirect:/login";
            }

            Patient patient = (Patient) currentUser;
            Doctor doctor = doctorService.getDoctorById(doctorId).orElse(null);

            if (doctor == null) {
                redirectAttributes.addFlashAttribute("error", "Doctor not found");
                return "redirect:/doctors/searchpage";
            }

            // Create new appointment
            Appointment appointment = new Appointment();
            appointment.setDoctor(doctor);
            appointment.setPatient(patient);
            appointment.setApptDate(apptDate);
            appointment.setApptTime(apptTime);
            appointment.setDuration(LocalTime.of(duration / 60, duration % 60));
            appointment.setCanceled(false);
            appointment.setRescheduled(false);

            // Set the IDs explicitly
            appointment.setDoctor_Id(doctor.getId());
            appointment.setPatient_Id(patient.getId());

            // Save the appointment
            appointmentService.createAppointment(appointment);

            // Update the patient's appointments list
            if (patient.getAppointments() == null) {
                patient.setAppointments(new ArrayList<>());
            }
            patient.getAppointments().add(appointment);
            patientService.updatePatient(patient);

            redirectAttributes.addFlashAttribute("success", "Appointment booked successfully!");
            return "redirect:/patients/home";
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Failed to book appointment. Please try again.");
            return "redirect:/doctors/searchpage";
        }
    }
}
