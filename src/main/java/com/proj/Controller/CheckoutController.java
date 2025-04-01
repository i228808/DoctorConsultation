package com.proj.Controller;

import com.proj.Bean.Patient;
import com.proj.Service.CheckoutService;
import com.proj.Service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/checkout")
public class CheckoutController {

    @Autowired
    private CheckoutService checkoutService;

    @Autowired
    private PatientService patientService;

    @PostMapping("/confirm")
    public String confirmCheckout(@RequestParam("patientId") int patientId, Model model) {
        // Retrieve patient details from the patient service
        Patient patient = patientService.getPatientById(patientId);
        if (patient != null) {
            // Confirm checkout for the patient using checkout service
            checkoutService.confirmCheckout(patient);
            // Add success message to the model
            model.addAttribute("success", "Checkout confirmed successfully!");
        } else {
            // Add error message to the model if patient not found
            model.addAttribute("error", "Patient not found!");
        }
        // Redirect back to the homepage
        return "redirect:/";
    }
}
