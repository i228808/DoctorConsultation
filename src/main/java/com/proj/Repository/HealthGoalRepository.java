package com.proj.Repository;

import com.proj.Bean.HealthGoal;
import com.proj.Bean.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HealthGoalRepository extends JpaRepository<HealthGoal, Integer> {
    @Query("SELECT h FROM HealthGoal h WHERE h.patient = :patient")
    List<HealthGoal> findByPatient(Patient patient);
}
