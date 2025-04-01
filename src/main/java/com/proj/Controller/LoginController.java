package com.proj.Controller;

import com.proj.Bean.Appointment;
import com.proj.Bean.Doctor;
import com.proj.Bean.Patient;
import com.proj.Service.AppointmentService;
import com.proj.Service.DoctorService;
import com.proj.Service.PatientService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/login")
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private PatientService patientService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("")
    public String getLoginPage(Model model) {
        // Check if user is already logged in
        if (model.containsAttribute("currentUser")) {
            Object user = model.getAttribute("currentUser");
            if (user instanceof Doctor) {
                return "redirect:/doctors/dashboard";
            } else if (user instanceof Patient) {
                return "redirect:/patients/home";
            }
        }
        return "login";
    }

    @PostMapping("")
    public String login(@RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "password", required = false) String password,
            RedirectAttributes redirectAttributes,
            Model model,
            HttpSession session) {
        try {
            // Validate input parameters
            if (email == null || password == null || email.trim().isEmpty() || password.trim().isEmpty()) {
                logger.warn("Empty email or password provided");
                redirectAttributes.addFlashAttribute("error", "Please enter both email and password");
                return "redirect:/login";
            }

            logger.info("Login attempt for email: {}", email);

            // First try to find a patient with the given credentials
            Patient patient = patientService.findPatientByEmailAndPassword(email, password);
            if (patient != null) {
                logger.info("Patient login successful: {}", email);
                session.setAttribute("currentUser", patient);
                session.setMaxInactiveInterval(30 * 60); // Set session timeout to 30 minutes
                return "redirect:/patients/home";
            }

            // If no patient found, try to find a doctor
            Doctor doctor = doctorService.findDoctorByEmailAndPassword(email, password);
            if (doctor != null) {
                logger.info("Doctor login successful: {}", email);
                session.setAttribute("currentUser", doctor);
                session.setMaxInactiveInterval(30 * 60); // Set session timeout to 30 minutes
                return "redirect:/doctors/dashboard";
            }

            // If neither patient nor doctor found, show error
            logger.warn("Login failed for email: {}", email);
            redirectAttributes.addFlashAttribute("error", "Invalid email or password. Please try again.");
            return "redirect:/login";
        } catch (Exception e) {
            logger.error("Error during login for email: {}", email, e);
            redirectAttributes.addFlashAttribute("error", "An error occurred during login. Please try again.");
            return "redirect:/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        logger.info("User logged out");
        session.invalidate();
        return "redirect:/login";
    }
}
