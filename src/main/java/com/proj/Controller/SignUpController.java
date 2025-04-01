package com.proj.Controller;

import com.proj.Bean.Doctor;
import com.proj.Bean.Patient;
import com.proj.Service.DoctorService;
import com.proj.Service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
@RequestMapping("/signup")
public class SignUpController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private DoctorService doctorService;

    @GetMapping("/patient")
    public String getPatientSignupPage() {
        return "patientsignup";
    }

    @GetMapping("/doctor")
    public String getDoctorSignupPage() {
        return "doctorsignup";
    }

    @PostMapping("/patient")
    public String signUpPatient(
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("phone") String phone,
            @RequestParam("dateOfBirth") LocalDate dateOfBirth,
            @RequestParam("address") String address,
            @RequestParam("gender") String gender,
            @RequestParam(value = "profilePicture", required = false) MultipartFile profilePicture,
            RedirectAttributes redirectAttributes) {

        if (patientService.findPatientByEmail(email)) {
            redirectAttributes.addFlashAttribute("error", "Email already exists. Please login to continue.");
            return "redirect:/login";
        }

        Patient patient = new Patient();
        patient.setFirstName(firstName);
        patient.setLastName(lastName);
        patient.setEmail(email);
        patient.setPassword(password);
        patient.setPhone(phone);
        patient.setDateOfBirth(dateOfBirth);
        patient.setAddress(address);
        patient.setGender(gender);

        // Handle profile picture if provided
        if (profilePicture != null && !profilePicture.isEmpty()) {
            // TODO: Implement profile picture handling
            patient.setProfilePicture("default-profile.png");
        }

        patientService.createPatient(patient);
        redirectAttributes.addFlashAttribute("success", "Account created successfully!");
        return "redirect:/AccountCreated";
    }

    @PostMapping("/doctor")
    public String signUpDoctor(
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("phone") String phone,
            @RequestParam("dateOfBirth") LocalDate dateOfBirth,
            @RequestParam("address") String address,
            @RequestParam("gender") String gender,
            @RequestParam("specialization") String specialization,
            @RequestParam("biography") String biography,
            @RequestParam(value = "profilePicture", required = false) MultipartFile profilePicture,
            RedirectAttributes redirectAttributes) {

        if (doctorService.findDoctorByEmail(email)) {
            redirectAttributes.addFlashAttribute("error", "Email already exists. Please login to continue.");
            return "redirect:/login";
        }

        Doctor doctor = new Doctor();
        doctor.setFirstName(firstName);
        doctor.setLastName(lastName);
        doctor.setEmail(email);
        doctor.setPassword(password);
        doctor.setPhone(phone);
        doctor.setDateOfBirth(dateOfBirth);
        doctor.setAddress(address);
        doctor.setGender(gender);
        doctor.setSpecialization(specialization);
        doctor.setBiography(biography);

        // Handle profile picture if provided
        if (profilePicture != null && !profilePicture.isEmpty()) {
            // TODO: Implement profile picture handling
            doctor.setProfilePicture("default-profile.png");
        }

        doctorService.addDoctor(doctor);
        redirectAttributes.addFlashAttribute("success", "Account created successfully!");
        return "redirect:/AccountCreated";
    }
}
