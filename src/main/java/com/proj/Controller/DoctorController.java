package com.proj.Controller;

// Bean Imports
import com.proj.Bean.Appointment;
import com.proj.Bean.Doctor;
import com.proj.Bean.Patient;
import com.proj.Bean.Rating;
import com.proj.Bean.MedicalRecord;
import com.proj.Bean.Prescription;
import com.proj.Bean.HealthGoal;

// Service Imports
import com.proj.Service.AppointmentService;
import com.proj.Service.DoctorService;
import com.proj.Service.PatientService;
import com.proj.Service.RatingService;
import com.proj.Service.HealthGoalService;
import com.proj.Service.MedicalRecordService;
import com.proj.Service.PrescriptionService;

// Servlet Import
import jakarta.servlet.http.HttpSession;

// Spring Framework Imports
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

// Java Util Imports
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller // Base path for all doctor endpoints
@RequestMapping("/doctors")
public class DoctorController {
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private RatingService ratingService;
    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private PatientService patientService;
    @Autowired
    private HealthGoalService healthGoalService;
    @Autowired
    private MedicalRecordService medicalRecordService;
    @Autowired
    private PrescriptionService prescriptionService;

    // Create a new doctor
    @PostMapping
    public ResponseEntity<Doctor> addDoctor(@RequestBody Doctor doctor) {
        Doctor savedDoctor = doctorService.addDoctor(doctor);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDoctor);
    }

    // Get all doctors
    // @GetMapping
    public List<Doctor> getAllDoctors() {
        return doctorService.getAllDoctors();
    }

    // Get doctor by ID
    @GetMapping("/{id}")
    public ResponseEntity<Doctor> getDoctorById(@PathVariable int id) {
        return doctorService.getDoctorById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get patients for a doctor
    @GetMapping("/{id}/patients")
    public List<Patient> getDoctorPatients(@PathVariable int id) {
        return doctorService.getDoctorPatients(id);
    }

    // Delete a doctor
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable int id) {
        doctorService.deleteDoctor(id);
        return ResponseEntity.noContent().build();
    }

    // Get doctors by specialization
    @GetMapping("/specialization/{specialization}")
    public List<Doctor> getDoctorsBySpecialization(@PathVariable String specialization) {
        return doctorService.getDoctorsBySpecialization(specialization);
    }

    // Get average rating for a doctor
    @GetMapping("/{doctorId}/average-rating")
    public ResponseEntity<Double> getAverageRatingForDoctor(@PathVariable int doctorId) {
        Double averageRating = doctorService.getAverageRatingForDoctor(doctorId);
        if (averageRating != null) {
            return ResponseEntity.ok(averageRating);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Get all ratings for a doctor
    @GetMapping("/{doctorId}/ratings/")
    public ResponseEntity<List<Rating>> getAllRatingsForDoctor(@PathVariable int doctorId) {
        List<Rating> ratings = doctorService.getAllRatingsForDoctor(doctorId);
        return ResponseEntity.ok(ratings);
    }

    // Doctor home page
    @GetMapping("/home")
    public String getHomePage() {
        return "doctorHome"; // Return the name of the Thymeleaf template
    }

    @GetMapping("/profile/{id}")
    public String getDoctorProfile(@PathVariable int id, Model model, HttpSession session) {
        try {
            // Get the current user from session
            Object currentUser = session.getAttribute("currentUser");
            if (!(currentUser instanceof Patient)) {
                return "redirect:/login";
            }
            Patient patient = (Patient) currentUser;
            model.addAttribute("patient", patient);

            // Get doctor details
            Optional<Doctor> doctorOptional = doctorService.getDoctorById(id);
            if (doctorOptional.isPresent()) {
                Doctor doctor = doctorOptional.get();

                // Set default values for optional fields
                if (doctor.getProfilePicture() == null) {
                    doctor.setProfilePicture("/images/default-avatar.png");
                }
                if (doctor.getPhone() == null) {
                    doctor.setPhone("Not provided");
                }
                if (doctor.getAddress() == null) {
                    doctor.setAddress("Not provided");
                }
                if (doctor.getBiography() == null) {
                    doctor.setBiography("No biography available.");
                }

                model.addAttribute("doctor", doctor);

                // Get ratings and ensure patient data is loaded
                List<Rating> ratings = doctor.getRatings();
                if (ratings != null) {
                    ratings.forEach(rating -> {
                        if (rating.getPatient() == null) {
                            Patient ratingPatient = patientService.getPatientById(rating.getPatientId());
                            if (ratingPatient != null) {
                                rating.setPatient(ratingPatient);
                            }
                        }
                    });
                } else {
                    ratings = new ArrayList<>();
                }
                model.addAttribute("ratings", ratings);

                // Calculate and format average rating
                Double averageRating = doctorService.getAverageRatingForDoctor(id);
                model.addAttribute("averageRating",
                        averageRating != null ? String.format("%.1f", averageRating) : "0.0");

                // Add new rating object for the form
                Rating newRating = new Rating();
                newRating.setDocId(id);
                newRating.setPatientId(patient.getId());
                model.addAttribute("newRating", newRating);

                // Add success/error messages if they exist
                if (model.containsAttribute("success")) {
                    model.addAttribute("success", model.getAttribute("success"));
                }
                if (model.containsAttribute("error")) {
                    model.addAttribute("error", model.getAttribute("error"));
                }

                return "DoctorProfile";
            } else {
                model.addAttribute("error", "Doctor not found.");
                return "errorPage";
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "An error occurred while loading the doctor profile.");
            return "errorPage";
        }
    }

    @PostMapping("/profile/{id}/add-ratings")
    public String addRating(@PathVariable int id,
            @ModelAttribute("newRating") Rating newRating,
            BindingResult bindingResult, HttpSession session,
            RedirectAttributes redirectAttributes) {
        try {
            if (bindingResult.hasErrors()) {
                redirectAttributes.addFlashAttribute("error", "Invalid rating.");
                return "redirect:/doctors/profile/" + id;
            }

            Object currentUser = session.getAttribute("currentUser");
            if (currentUser instanceof Patient) {
                Patient patient = (Patient) currentUser;
                newRating.setDocId(id);
                newRating.setPatientId(patient.getId());
                newRating.setDate(LocalDate.now());

                // Save the rating
                ratingService.addRating(newRating);

                redirectAttributes.addFlashAttribute("success", "Review added successfully!");
                return "redirect:/doctors/profile/" + id;
            } else {
                redirectAttributes.addFlashAttribute("error", "You must be logged in as a patient to leave a review.");
                return "redirect:/doctors/profile/" + id;
            }
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "An error occurred while adding your review.");
            return "redirect:/doctors/profile/" + id;
        }
    }

    @GetMapping("/myappointments")
    public String getMyAppointments(HttpSession session, Model model) {
        Object user = session.getAttribute("currentUser");
        if (user instanceof Doctor) {
            Doctor doctor = (Doctor) user;
            model.addAttribute("doctor", doctor);

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

            model.addAttribute("pastAppointments", pastAppointments);
            model.addAttribute("upcomingAppointments", upcomingAppointments);
            return "myAppointments";
        }
        return "redirect:/";
    }

    @GetMapping("/my-patients")
    public String getMyPatients(HttpSession session, Model model) {
        Object user = session.getAttribute("currentUser");
        if (user instanceof Doctor) {
            Doctor doctor = (Doctor) user;
            model.addAttribute("doctor", doctor);

            // Get unique patients from appointments
            List<Patient> patients = new ArrayList<>();
            doctor.getAppointments().forEach(appointment -> {
                if (!patients.contains(appointment.getPatient())) {
                    patients.add(appointment.getPatient());
                }
            });

            model.addAttribute("patients", patients);
            return "myPatients";
        }
        return "redirect:/login";
    }

    @GetMapping("/")
    public String root() {
        return "redirect:/login";
    }

    @GetMapping("/manage-profile")
    public String getManageProfile(HttpSession session, Model model) {
        Object user = session.getAttribute("currentUser");
        if (user instanceof Doctor) {
            Doctor doctor = (Doctor) user;
            model.addAttribute("doctor", doctor);
            return "manageProfile";
        }
        return "redirect:/";
    }

    @PostMapping("/update-profile")
    public String updateProfile(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName,
            @RequestParam("email") String email, @RequestParam("specialization") String specialization,
            @RequestParam("biography") String biography, HttpSession session, RedirectAttributes redirectAttributes) {
        Object user = session.getAttribute("currentUser");
        if (user instanceof Doctor) {
            Doctor doctor = (Doctor) user;
            doctor.setFirstName(firstName);
            doctor.setLastName(lastName);
            doctor.setEmail(email);
            doctor.setSpecialization(specialization);
            doctor.setBiography(biography);
            Doctor updatedDoctor = doctorService.updateDoctor(doctor);
            session.setAttribute("currentUser", updatedDoctor);
            redirectAttributes.addFlashAttribute("success", "Profile updated successfully!");
            return "redirect:/doctors/dashboard";
        }

        return "redirect:/doctor/dashboard";
    }

    @GetMapping("/dashboard")
    public String getDashboard(HttpSession session, Model model) {
        Object user = session.getAttribute("currentUser");

        if (user instanceof Doctor) {
            Doctor doctor = (Doctor) user;
            if (doctor != null) {
                session.setAttribute("currentUser", doctor);
                model.addAttribute("doctor", doctor);

                // Get total patients count
                List<Patient> patientList = new ArrayList<>();
                doctor.getAppointments().forEach(appointment -> patientList.add(appointment.getPatient()));
                model.addAttribute("totalPatients", patientList.size());

                // Get appointments for today
                model.addAttribute("appointmentsToday",
                        appointmentService.getAppointmentsByDoctorIdAndDate(doctor.getId()));

                // Get average rating
                model.addAttribute("averageRating", doctorService.getAverageRating(doctor.getId()));

                // Get prescriptions count safely
                int prescriptionCount = 0;
                try {
                    prescriptionCount = doctor.getPrescriptions().size();
                } catch (Exception e) {
                    // If lazy loading fails, set to 0
                    prescriptionCount = 0;
                }
                model.addAttribute("prescriptions", prescriptionCount);

                // Get upcoming appointments safely
                List<Appointment> upcomingAppointments = new ArrayList<>();
                try {
                    List<Appointment> allAppointments = doctor.getAppointments();
                    for (Appointment appointment : allAppointments) {
                        if (appointment.getApptDate().isAfter(java.time.LocalDate.now())) {
                            // Ensure patient data is loaded
                            if (appointment.getPatient() == null) {
                                Patient patient = patientService.getPatientById(appointment.getPatient_Id());
                                if (patient != null) {
                                    appointment.setPatient(patient);
                                }
                            }
                            upcomingAppointments.add(appointment);
                        }
                    }
                } catch (Exception e) {
                    // If lazy loading fails, set empty list
                    upcomingAppointments = new ArrayList<>();
                }
                model.addAttribute("upcomingAppointments", upcomingAppointments);

                // Get latest reviews safely
                try {
                    List<Rating> ratings = doctor.getRatings();
                    if (ratings != null) {
                        // Ensure patient data is loaded for each rating
                        for (Rating rating : ratings) {
                            if (rating.getPatient() == null) {
                                Patient patient = patientService.getPatientById(rating.getPatientId());
                                if (patient != null) {
                                    rating.setPatient(patient);
                                }
                            }
                        }
                        model.addAttribute("latestReviews", ratings);
                    } else {
                        model.addAttribute("latestReviews", new ArrayList<>());
                    }
                } catch (Exception e) {
                    // If lazy loading fails, set empty list
                    model.addAttribute("latestReviews", new ArrayList<>());
                }
            }
        }
        return "DoctorDashboard";
    }

    @GetMapping("/patient-profile/{id}")
    public String getPatientProfile(@PathVariable int id, HttpSession session, Model model) {
        Object user = session.getAttribute("currentUser");
        if (user instanceof Doctor) {
            Doctor doctor = (Doctor) user;
            model.addAttribute("doctor", doctor);

            Patient patient = patientService.getPatientById(id);
            if (patient != null) {
                model.addAttribute("patient", patient);
                // Add flash attributes if they exist
                if (model.containsAttribute("success")) {
                    model.addAttribute("success", model.getAttribute("success"));
                }
                if (model.containsAttribute("error")) {
                    model.addAttribute("error", model.getAttribute("error"));
                }
                return "patientProfile";
            }
        }
        return "redirect:/login";
    }

    @PostMapping("/patient-profile/{id}/add-record")
    public String addMedicalRecord(@PathVariable int id,
            @RequestParam("descript") String descript,
            @RequestParam("recordDate") LocalDate recordDate,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        Object user = session.getAttribute("currentUser");
        if (user instanceof Doctor) {
            Doctor doctor = (Doctor) user;
            Patient patient = patientService.getPatientById(id);

            if (patient != null) {
                MedicalRecord record = new MedicalRecord(descript, recordDate, patient);
                medicalRecordService.createMedicalRecord(record);
                redirectAttributes.addFlashAttribute("success", "Medical record added successfully!");
            } else {
                redirectAttributes.addFlashAttribute("error", "Patient not found!");
            }
        }
        return "redirect:/doctors/patient-profile/" + id;
    }

    @PostMapping("/patient-profile/{id}/add-prescription")
    public String addPrescription(@PathVariable int id,
            @RequestParam("medicationDetails") String medicationDetails,
            @RequestParam("dosage") String dosage,
            @RequestParam("dateUntil") LocalDate dateUntil,
            @RequestParam("instructions") String instructions,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        try {
            Object user = session.getAttribute("currentUser");
            if (user instanceof Doctor) {
                Doctor doctor = (Doctor) user;
                Patient patient = patientService.getPatientById(id);

                if (patient != null) {
                    Prescription prescription = new Prescription(doctor, patient, medicationDetails, LocalDate.now(),
                            dosage, dateUntil, instructions);
                    prescriptionService.createPrescription(prescription);
                    redirectAttributes.addFlashAttribute("success", "Prescription added successfully!");
                } else {
                    redirectAttributes.addFlashAttribute("error", "Patient not found!");
                }
            } else {
                redirectAttributes.addFlashAttribute("error",
                        "You must be logged in as a doctor to add prescriptions.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "An error occurred while adding the prescription.");
        }
        return "redirect:/doctors/patient-profile/" + id;
    }

    @PostMapping("/patient-profile/{id}/add-health-goal")
    public String addHealthGoal(@PathVariable int id,
            @RequestParam("goalDescription") String goalDescription,
            @RequestParam("targetValue") int targetValue,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        Object user = session.getAttribute("currentUser");
        if (user instanceof Doctor) {
            Doctor doctor = (Doctor) user;
            Patient patient = patientService.getPatientById(id);

            if (patient != null) {
                HealthGoal healthGoal = new HealthGoal(doctor, patient, goalDescription, targetValue, "Pending",
                        LocalDate.now(), LocalDate.now());

                // Create the health goal using the service
                healthGoalService.createHealthGoal(healthGoal);

                redirectAttributes.addFlashAttribute("success", "Health goal added successfully!");
            } else {
                redirectAttributes.addFlashAttribute("error", "Patient not found!");
            }
        }
        return "redirect:/doctors/patient-profile/" + id;
    }
}
