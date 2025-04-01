package com.proj.Controller;

import com.proj.Bean.Doctor;
import com.proj.Bean.Patient;
import com.proj.Bean.Prescription;
import com.proj.Service.DoctorService;
import com.proj.Service.PatientService;
import com.proj.Service.PrescriptionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequestMapping("/prescriptions")
public class prescriptonController {

    @Autowired
    private PrescriptionService prescriptionService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

    @PostMapping("/create")
    public String createPrescription(@RequestParam("doctorId") int doctorId,
                                     @RequestParam("patientId") int patientId,
                                     @RequestParam("medicationDetails") String medicationDetails,
                                     Model model) {

        Optional<Doctor> doctor = doctorService.getDoctorById(doctorId);
        Optional<Patient> patient = Optional.ofNullable(patientService.getPatientById(patientId));

        if (doctor.isPresent() && patient.isPresent()) {
            Prescription prescription = new Prescription(doctor.get(), patient.get(), medicationDetails, LocalDate.now());
            prescriptionService.createPrescription(prescription);
            model.addAttribute("success", "Prescription created successfully!");
        } else {
            model.addAttribute("error", "Doctor or Patient not found!");
        }

        return "redirect:/doctors/profile/" + doctorId;
    }

    @GetMapping
    public ModelAndView getAllPrescriptions(HttpSession session){

        Object currentUser = session.getAttribute("currentUser");
         if (currentUser instanceof Patient) {
            Patient patient = (Patient) currentUser;

            ModelAndView modelAndView = new ModelAndView("Prescriptions");
        modelAndView.addObject("prescriptions", prescriptionService.getPrescriptionsByPatientId(patient.getId()));
        modelAndView.addObject("Patient", patient);
        return modelAndView;}
        else {
            return new ModelAndView("redirect:/login");
        }
    }
    @PostMapping("/set-prescription/{id}")
    public String setPrescription(@PathVariable("id") int id,@RequestParam("medication") String medication,@RequestParam("dosage") String Dosage,@RequestParam("duration") int duration,
                                  @RequestParam("instructions") String instructions,HttpSession session, Model model) {
        Object User = session.getAttribute("currentUser");
        if (User instanceof Doctor) {
            Doctor doctor = (Doctor) User;

            Patient patient = patientService.getPatientById(id);

            LocalDate startDate = LocalDate.now();
            LocalDate endDate = startDate.plusDays(duration);

            Prescription prescription = new Prescription(doctor, patientService.getPatientById(id), medication, LocalDate.now(), Dosage, LocalDate.now().plusDays(duration), instructions);
            prescriptionService.createPrescription(prescription);
            model.addAttribute("success", "Prescription created successfully!");
        }
        else{
            model.addAttribute("error", "Doctor not found!");
        }


        return "redirect:/patients/profile"+id;
    }
    @GetMapping("/{id}")
    public String getPrescriptionById(@PathVariable("id") int id, Model model) {
        Optional<Prescription> prescription = prescriptionService.getPrescriptionById(id);
        if (prescription.isPresent()) {
            model.addAttribute("prescription", prescription.get());
        } else {
            model.addAttribute("error", "Prescription not found!");
        }
        return "prescriptions/detail";
    }

    @PostMapping("/delete/{id}")
    public String deletePrescription(@PathVariable("id") int id, Model model) {
        prescriptionService.deletePrescriptionById(id);
        return "redirect:/prescriptions";
    }
}
