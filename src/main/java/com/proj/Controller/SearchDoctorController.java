package com.proj.Controller;

import com.proj.Bean.Doctor;
import com.proj.Bean.Patient;
import com.proj.Service.DoctorService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/doctors")
public class SearchDoctorController {
    @Autowired
    private DoctorService doctorService;

    @GetMapping("/searchpage")
    public String getSearchPage(Model model, HttpSession session) {
        try {
            Object currentUser = session.getAttribute("currentUser");
            if (currentUser instanceof Patient) {
                Patient patient = (Patient) currentUser;
                model.addAttribute("Patient", patient);

                // Add list of specializations
                List<String> specializations = Arrays.asList(
                        "Cardiology", "Dermatology", "Neurology", "Pediatrics",
                        "Orthopedics", "Ophthalmology", "ENT", "Dental");
                model.addAttribute("specializations", specializations);

                // Add initial list of all doctors
                List<Doctor> doctors = doctorService.getAllDoctors();
                model.addAttribute("doctors", doctors);

                return "searchPage";
            }
            return "redirect:/login";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "An error occurred while loading the search page. Please try again.");
            return "errorPage";
        }
    }

    @GetMapping("/search")
    public String searchDoctors(
            @RequestParam(name = "specialization", required = false) String specialization,
            @RequestParam(name = "location", required = false) String location,
            @RequestParam(name = "search", required = false) String search,
            HttpSession session,
            Model model) {

        try {
            Object currentUser = session.getAttribute("currentUser");
            if (!(currentUser instanceof Patient)) {
                return "redirect:/login";
            }

            Patient patient = (Patient) currentUser;
            List<Doctor> doctors = new ArrayList<>();

            if (specialization != null && !specialization.isEmpty()) {
                doctors.addAll(doctorService.getDoctorsBySpecialization(specialization));
            }

            if (search != null && !search.isEmpty()) {
                String[] names = search.split(" ");
                if (names.length == 1) {
                    doctors.addAll(doctorService.findDoctorsByName(names[0], ""));
                } else {
                    doctors.addAll(doctorService.findDoctorsByName(names[0], names[1]));
                }
            }

            // If no search criteria provided, show all doctors
            if ((specialization == null || specialization.isEmpty()) &&
                    (search == null || search.isEmpty())) {
                doctors = doctorService.getAllDoctors();
            }

            // Add list of specializations for the dropdown
            List<String> specializations = Arrays.asList(
                    "Cardiology", "Dermatology", "Neurology", "Pediatrics",
                    "Orthopedics", "Ophthalmology", "ENT", "Dental");
            model.addAttribute("specializations", specializations);
            model.addAttribute("doctors", doctors);
            model.addAttribute("Patient", patient);

            return "searchPage";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "An error occurred while searching for doctors. Please try again.");
            return "errorPage";
        }
    }
}
