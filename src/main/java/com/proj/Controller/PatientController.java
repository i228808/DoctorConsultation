package com.proj.Controller;

import com.proj.Bean.*;
import com.proj.Service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;
    @Autowired
    private MedicalRecordService medicalRecordService;
    // Patient CRUD operations
    @Autowired
    private HealthGoalService healthGoalService;
    @Autowired
    private PrescriptionService prescriptionService;

    @GetMapping
    public List<Patient> getAllPatients() {
        return patientService.getAllPatients();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable int id) {
        Patient patient = patientService.getPatientById(id);
        return ResponseEntity.ok(patient);
    }

    @GetMapping("/profile/{id}")
    public String getPatientProfile(@PathVariable int id, Model model) {
        Patient patient = patientService.getPatientById(id);
        model.addAttribute("patient", patient);
        model.addAttribute("medicalRecords", patient.getMedicalRecords());

        return "patientProfile";

    }

    @PostMapping
    public ResponseEntity<Patient> createPatient(@RequestBody Patient patient) {
        Patient createdPatient = patientService.createPatient(patient);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPatient);
    }

    @PostMapping("/profile/prescriptions/set-prescription/{id}")
    public String setPrescription(@PathVariable("id") int id, @RequestParam("medication") String medication,
            @RequestParam("dosage") String Dosage, @RequestParam("duration") int duration,
            @RequestParam("instructions") String instructions, HttpSession session, Model model) {
        Object User = session.getAttribute("currentUser");
        if (User instanceof Doctor) {
            Doctor doctor = (Doctor) User;

            Patient patient = patientService.getPatientById(id);

            LocalDate startDate = LocalDate.now();
            LocalDate endDate = startDate.plusDays(duration);

            Prescription prescription = new Prescription(doctor, patientService.getPatientById(id), medication,
                    LocalDate.now(), Dosage, LocalDate.now().plusDays(duration), instructions);
            prescriptionService.createPrescription(prescription);
            model.addAttribute("success", "Prescription created successfully!");
        } else {
            model.addAttribute("error", "Doctor not found!");
        }

        return "redirect:/patients/profile/" + id;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable int id, @RequestBody Patient patient) {
        patient.setId(id); // Ensure the ID is correct
        Patient updatedPatient = patientService.updatePatient(patient);
        return ResponseEntity.ok(updatedPatient);
    }

    @PostMapping("/profile/{id}/add-record")
    public String addMedicalRecord(@PathVariable int id,
            @RequestParam("descript") String descript,
            @RequestParam("recordDate") LocalDate recordDate,
            RedirectAttributes redirectAttributes) {
        Patient patient = patientService.getPatientById(id);
        MedicalRecord medicalRecord = new MedicalRecord(descript, recordDate, patient);
        medicalRecordService.createMedicalRecord(medicalRecord);
        redirectAttributes.addFlashAttribute("success", "Medical record added successfully!");
        return "redirect:/patients/profile/" + id;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable int id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/my-patients")
    public String getMyPatients(HttpSession session, Model model) {
        Object User = session.getAttribute("currentUser");
        if (User instanceof Doctor) {
            Doctor doctor = (Doctor) User;
            List<Patient> patients = patientService.getPatientsByDoctor(doctor);
            model.addAttribute("patients", patients);
            return "myPatients";
        }
        return "redirect:/";
    }

    @GetMapping("view-health-goals")
    public ModelAndView GetHealthGoals(HttpSession session) {
        Object user = session.getAttribute("currentUser");
        if (user instanceof Patient) {
            Patient patient = (Patient) user;
            List<HealthGoal> healthGoals = healthGoalService.getHealthGoalsByPatient(patient);
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("ViewHealthGoals");
            modelAndView.addObject("healthGoals", healthGoals);
            modelAndView.addObject("patient", patient);
            return modelAndView;
        } else {
            return new ModelAndView("redirect:/patients/home");
        }
    }

    @PostMapping("/profile/{id}/set-goal")
    public String setGoal(@PathVariable int id, @RequestParam("description") String goal,
            @RequestParam("target") int Target, HttpSession session, RedirectAttributes redirectAttributes) {
        Patient patient = patientService.getPatientById(id);
        Object User = session.getAttribute("currentUser");
        if (!(User instanceof Doctor)) {
            redirectAttributes.addFlashAttribute("error", "Doctor not found!");
            return "redirect:/patients/profile/" + id;
        }
        Doctor doctor = (Doctor) User;
        LocalDate startDate = LocalDate.now();
        LocalDate updatedAt = LocalDate.now();
        HealthGoal healthGoal = new HealthGoal(doctor, patient, goal, Target, "Pending", startDate, updatedAt);
        /*
         * patient.setHealthGoal(healthGoal);
         * patientService.updatePatient(patient);
         */
        healthGoalService.createHealthGoal(healthGoal);
        redirectAttributes.addFlashAttribute("success", "Goal set successfully!");
        return "redirect:/patients/profile/" + id;
    }

    @GetMapping("/home")
    public String getHome(HttpSession session, Model model, RedirectAttributes attributes) {
        try {
            Object user = session.getAttribute("currentUser");
            if (user == null) {
                attributes.addFlashAttribute("error", "Please login to access this page");
                return "redirect:/login";
            }

            if (user instanceof Patient) {
                Patient patient = (Patient) user;
                model.addAttribute("userId", patient.getId());
                model.addAttribute("Patient", patient);

                // Initialize empty lists
                List<Appointment> appointments = new ArrayList<>();
                List<Doctor> doctorList = new ArrayList<>();
                List<MedicalRecord> medicalRecords = new ArrayList<>();
                List<HealthGoal> healthGoals = new ArrayList<>();
                List<Prescription> prescriptions = new ArrayList<>();

                try {
                    // Safely handle appointments
                    if (patient.getAppointments() != null) {
                        appointments = patient.getAppointments().stream()
                                .filter(appointment -> appointment != null && appointment.getDoctor() != null)
                                .collect(Collectors.toList());

                        // Create doctor list from valid appointments
                        doctorList = appointments.stream()
                                .map(Appointment::getDoctor)
                                .filter(doctor -> doctor != null)
                                .collect(Collectors.toList());
                    }

                    // Safely handle medical records
                    if (patient.getMedicalRecords() != null) {
                        medicalRecords = patient.getMedicalRecords().stream()
                                .filter(record -> record != null)
                                .collect(Collectors.toList());
                    }

                    // Safely handle health goals
                    if (patient.getHealthGoals() != null) {
                        healthGoals = patient.getHealthGoals().stream()
                                .filter(goal -> goal != null)
                                .collect(Collectors.toList());
                    }

                    // Safely handle prescriptions
                    if (patient.getPrescriptions() != null) {
                        prescriptions = patient.getPrescriptions().stream()
                                .filter(prescription -> prescription != null)
                                .collect(Collectors.toList());
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    // If there's an error with any collection, continue with empty lists
                }

                // Add all collections to the model
                model.addAttribute("doctors", doctorList);
                model.addAttribute("appointments", appointments);
                model.addAttribute("medicalRecords", medicalRecords);
                model.addAttribute("healthGoals", healthGoals);
                model.addAttribute("prescriptions", prescriptions);

                return "PatientHome";
            } else {
                attributes.addFlashAttribute("error", "Please login as a patient to access this page");
                return "redirect:/login";
            }
        } catch (Exception e) {
            e.printStackTrace(); // Add stack trace for debugging
            attributes.addFlashAttribute("error", "An error occurred. Please try again.");
            return "redirect:/login";
        }
    }

    @PostMapping("/signup")
    public String signupPatient(@RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("phone") String phone,
            @RequestParam("dateOfBirth") LocalDate dateOfBirth,
            @RequestParam("address") String address,
            RedirectAttributes redirectAttributes) {
        try {
            Patient patient = new Patient();
            patient.setFirstName(firstName);
            patient.setLastName(lastName);
            patient.setEmail(email);
            patient.setPassword(password);
            patient.setPhone(phone);
            patient.setDateOfBirth(dateOfBirth);
            patient.setAddress(address);

            Patient createdPatient = patientService.createPatient(patient);
            redirectAttributes.addFlashAttribute("success", "Account created successfully!");
            return "redirect:/AccountCreated";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to create account. Please try again.");
            return "redirect:/signup/patient";
        }
    }

    @GetMapping("/manage-profile")
    public String getManageProfile(HttpSession session, Model model, RedirectAttributes attributes) {
        try {
            Object user = session.getAttribute("currentUser");
            if (user == null) {
                attributes.addFlashAttribute("error", "Please login to access this page");
                return "redirect:/login";
            }

            if (user instanceof Patient) {
                Patient patient = (Patient) user;
                model.addAttribute("patient", patient);
                return "ManagePatientProfile";
            } else {
                attributes.addFlashAttribute("error", "Please login as a patient to access this page");
                return "redirect:/login";
            }
        } catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute("error", "An error occurred. Please try again.");
            return "redirect:/login";
        }
    }

    @PostMapping("/update-profile")
    public String updateProfile(
            @RequestParam(value = "profilePicture", required = false) MultipartFile profilePicture,
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("email") String email,
            @RequestParam("phone") String phone,
            @RequestParam("address") String address,
            @RequestParam("dateOfBirth") LocalDate dateOfBirth,
            @RequestParam(value = "currentPassword", required = false) String currentPassword,
            @RequestParam(value = "newPassword", required = false) String newPassword,
            @RequestParam(value = "confirmPassword", required = false) String confirmPassword,
            HttpSession session,
            RedirectAttributes attributes) {
        try {
            Object user = session.getAttribute("currentUser");
            if (user == null || !(user instanceof Patient)) {
                attributes.addFlashAttribute("error", "Please login as a patient to update your profile");
                return "redirect:/login";
            }

            Patient patient = (Patient) user;

            // Update basic information
            patient.setFirstName(firstName);
            patient.setLastName(lastName);
            patient.setEmail(email);
            patient.setPhone(phone);
            patient.setAddress(address);
            patient.setDateOfBirth(dateOfBirth);

            // Handle profile picture upload if provided
            if (profilePicture != null && !profilePicture.isEmpty()) {
                // TODO: Implement file upload logic and save the file path
                // For now, we'll just update the profile picture path
                String fileName = profilePicture.getOriginalFilename();
                // You should implement proper file storage logic here
                patient.setProfilePicture("/uploads/profile-pictures/" + fileName);
            }

            // Handle password change if requested
            if (currentPassword != null && !currentPassword.isEmpty()) {
                if (!patient.getPassword().equals(currentPassword)) {
                    attributes.addFlashAttribute("error", "Current password is incorrect");
                    return "redirect:/patients/manage-profile";
                }

                if (newPassword == null || newPassword.isEmpty()) {
                    attributes.addFlashAttribute("error", "New password cannot be empty");
                    return "redirect:/patients/manage-profile";
                }

                if (!newPassword.equals(confirmPassword)) {
                    attributes.addFlashAttribute("error", "New passwords do not match");
                    return "redirect:/patients/manage-profile";
                }

                patient.setPassword(newPassword);
            }

            // Update the patient in the database
            Patient updatedPatient = patientService.updatePatient(patient);

            // Update the session with the new patient data
            session.setAttribute("currentUser", updatedPatient);

            attributes.addFlashAttribute("success", "Profile updated successfully!");
            return "redirect:/patients/manage-profile";
        } catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute("error", "An error occurred while updating your profile. Please try again.");
            return "redirect:/patients/manage-profile";
        }
    }

}