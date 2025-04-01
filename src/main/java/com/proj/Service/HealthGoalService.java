package com.proj.Service;

import com.proj.Bean.HealthGoal;
import com.proj.Bean.Patient;
import com.proj.Repository.HealthGoalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HealthGoalService {
    private final HealthGoalRepository healthGoalRepository;

    @Autowired
    public HealthGoalService(HealthGoalRepository healthGoalRepository) {
        this.healthGoalRepository = healthGoalRepository;
    }

    public HealthGoal createHealthGoal(HealthGoal healthGoal) {
        return healthGoalRepository.save(healthGoal);
    }

    public List<HealthGoal> getAllHealthGoals() {
        return healthGoalRepository.findAll();
    }

    public Optional<HealthGoal> getHealthGoalById(int id) {
        return healthGoalRepository.findById(id);
    }

    public void deleteHealthGoalById(int id) {
        healthGoalRepository.deleteById(id);
    }

    public List<HealthGoal> getHealthGoalsByPatient(Patient patient) {
        return healthGoalRepository.findByPatient(patient);
    }

    public void updateHealthGoal(HealthGoal healthGoal) {
        healthGoalRepository.save(healthGoal);
    }
}
