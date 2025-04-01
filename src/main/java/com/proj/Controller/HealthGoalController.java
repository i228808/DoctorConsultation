package com.proj.Controller;

import com.proj.Bean.HealthGoal;
import com.proj.Service.HealthGoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/health-goals")
public class HealthGoalController {

    private final HealthGoalService healthGoalService;

    @Autowired
    public HealthGoalController(HealthGoalService healthGoalService) {
        this.healthGoalService = healthGoalService;
    }

    @PostMapping("/create")
    public ResponseEntity<HealthGoal> createHealthGoal(@RequestBody HealthGoal healthGoal) {
        HealthGoal savedHealthGoal = healthGoalService.createHealthGoal(healthGoal);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedHealthGoal);
    }

    @GetMapping
    public List<HealthGoal> getAllHealthGoals() {
        return healthGoalService.getAllHealthGoals();
    }

    @GetMapping("/{id}")
    public ResponseEntity<HealthGoal> getHealthGoalById(@PathVariable("id") int id) {
        Optional<HealthGoal> healthGoal = healthGoalService.getHealthGoalById(id);
        return healthGoal.map(value -> ResponseEntity.ok().body(value))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHealthGoalById(@PathVariable("id") int id) {
        healthGoalService.deleteHealthGoalById(id);
        return ResponseEntity.noContent().build();
    }
}
