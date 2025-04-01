package com.proj.Bean;
import java.io.Serializable;

public class RatingId implements Serializable {
    public RatingId(int id, int id2, int ratingId2) {
        this.docId = id;
        this.patientId = id2;
        this.ratingId = ratingId2;
    }
    public RatingId(RatingId ratingId) {
        this.docId = ratingId.getDocId();
        this.patientId = ratingId.getPatientId();
        this.ratingId = ratingId.getRatingId();
    }
    private int docId;
    private int patientId;
    private int ratingId;
    // ... equals and hashCode methods
    public int getDocId() {
        return docId;
    }
    public void setDocId(int docId) {
        this.docId = docId;
    }
    public int getPatientId() {
        return patientId;
    }
    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }
    public int getRatingId() {
        return ratingId;
    }
    public void setRatingId(int ratingId) {
        this.ratingId = ratingId;
    }
} 