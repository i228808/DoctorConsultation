package com.proj.Service;

import com.proj.Bean.Rating;
import com.proj.Bean.RatingId;
import com.proj.Repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    // Create


    // Read
    public List<Rating> getAllRatings() {
        return ratingRepository.findAll();
    }

    public Rating getRatingById(RatingId ratingId) {
        return ratingRepository.findById(ratingId).orElse(null); // Return null if not found
    }

    public List<Rating> getRatingsByDoctorId(int doctorId) {
        return ratingRepository.findByDoctorId(doctorId);
    }


    // Update (without validation)
    public Rating updateRating(RatingId ratingId, Rating ratingDetails) {
        // Assuming the rating exists (no error handling)
        Rating rating = ratingRepository.findById(ratingId).orElse(null); // Return null if not found
        if(rating!=null){
            rating.setStars(ratingDetails.getStars());
            rating.setDescript(ratingDetails.getDescript());
            return ratingRepository.save(rating);
        }
        else{
            return null;
        }

    }

    // Delete
    public void deleteRating(RatingId ratingId) {
        // Assuming the rating exists (no error handling)
        ratingRepository.deleteById(ratingId);
    }

    public void addRating(Rating newRating) {

       try{ ratingRepository.save(newRating);}
       catch (Exception e){
           System.out.println("Error in adding rating");
       }
    }
}
