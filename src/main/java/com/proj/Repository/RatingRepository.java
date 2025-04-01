package com.proj.Repository;

import com.proj.Bean.Rating;
import com.proj.Bean.RatingId; // Import the correct composite ID class
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface RatingRepository extends JpaRepository<Rating, RatingId> {  // Use RatingId as the second generic type
 
    // Custom query methods (e.g., find average rating for a doctor)
    @Query("SELECT AVG(r.stars) FROM Rating r WHERE r.doctor.id = :doctorId")
    Double findAverageRatingByDoctorId(int doctorId);
    // List<Rating> findByDoctorId(int doctorId);
    @Query("SELECT r FROM Rating r WHERE r.doctor.id = :doctorId")
    List<Rating> findByDoctorId(int doctorId);
}
