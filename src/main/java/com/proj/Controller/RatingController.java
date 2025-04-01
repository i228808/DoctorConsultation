package com.proj.Controller;

import com.proj.Bean.Patient;
import com.proj.Bean.Rating;
import com.proj.Bean.RatingId;
import com.proj.Service.DoctorService;
import com.proj.Service.RatingService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.proj.Service.DoctorService;
@Controller
@RequestMapping("/ratings") // Base path for rating endpoints
public class RatingController {
    DoctorService doctorService;
    @Autowired
    private RatingService ratingService;

    // Create a new rating
    
    // Get all ratings
    @GetMapping
    public List<Rating> getAllRatings() {
        return ratingService.getAllRatings();
    }
    @GetMapping("/doctor/{doctorId}/avg")
    public double getAverageDoctorRating(int DoctorId) {
        return doctorService.getAverageRatingForDoctor(DoctorId);
    }
    
    // Get rating by ID (composite key)
    @GetMapping("/{doctorId}/{patientId}/{ratingId}")
    public ResponseEntity<Rating> getRatingById(
            @PathVariable int doctorId,
            @PathVariable int patientId,
            @PathVariable int ratingId
    ) {
        RatingId id = new RatingId(doctorId, patientId, ratingId);
        Rating rating = ratingService.getRatingById(id);
        return rating != null ? ResponseEntity.ok(rating) : ResponseEntity.notFound().build();
    }

    // Get ratings by doctor ID
    @GetMapping("/doctor/{doctorId}")
    public List<Rating> getRatingsByDoctorId(@PathVariable int doctorId) {
        return ratingService.getRatingsByDoctorId(doctorId);
    }

    // Update a rating (using composite key)
    @PutMapping("/{doctorId}/{patientId}/{ratingId}")
    public ResponseEntity<Rating> updateRating(
            @PathVariable int doctorId,
            @PathVariable int patientId,
            @PathVariable int ratingId,
            @RequestBody Rating ratingDetails
    ) {
        RatingId id = new RatingId(doctorId, patientId, ratingId);
        Rating updatedRating = ratingService.updateRating(id, ratingDetails);
        return updatedRating != null ? ResponseEntity.ok(updatedRating) : ResponseEntity.notFound().build();
    }

    // Delete a rating (using composite key)
    @DeleteMapping("/{doctorId}/{patientId}/{ratingId}")
    public ResponseEntity<Void> deleteRating(
            @PathVariable int doctorId,
            @PathVariable int patientId,
            @PathVariable int ratingId
    ) {
        RatingId id = new RatingId(doctorId, patientId, ratingId);
        ratingService.deleteRating(id); 
        return ResponseEntity.noContent().build();
    }
}
