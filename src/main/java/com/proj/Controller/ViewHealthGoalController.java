package com.proj.Controller;

import com.proj.Bean.HealthGoal;
import com.proj.Service.HealthGoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.time.LocalDate;
import java.util.Optional;

@Controller
public class ViewHealthGoalController {


    @Autowired
    private HealthGoalService healthGoalService;


    @GetMapping("/patients/update-health-goal")
    public String showUpdateHealthGoalForm(@RequestParam("id") int id, Model model) {
        Optional<HealthGoal> healthGoal = healthGoalService.getHealthGoalById(id);
        model.addAttribute("healthGoal", healthGoal);
        return "updateHealthGoal";
    }

    @PostMapping("/patients/update-health-goal")
    public String updateHealthGoal(@ModelAttribute("healthGoal") HealthGoal healthGoal) {
        healthGoalService.updateHealthGoal(healthGoal);
        return "redirect:/view-health-goals";}
    @PostMapping("/updateProgress")
    public String updateProgress(@RequestParam("goalId") int goalId, @RequestParam("progress") int progress) {
        Optional<HealthGoal> goal = healthGoalService.getHealthGoalById(goalId);
        if (goal != null) {
            goal.get().setProgress(progress);
            if(goal.get().getProgress() == goal.get().getTargetValue()){
                goal.get().setStatus("Completed");
                goal.get().setUpdatedAt(LocalDate.now());
                }
            else{
                goal.get().setStatus("Pending");
                goal.get().setUpdatedAt(LocalDate.now());
            }
            healthGoalService.updateHealthGoal(goal.get());
        }
        return "redirect:/patients/view-health-goals";
    }


}
