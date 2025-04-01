// Create a separate class for the composite primary key
package com.proj.Bean;
import java.io.Serializable;
public class AppointmentId implements Serializable {
    public AppointmentId(int doctorId2, int patientId2, int apptId2) {
        this.doctorId = doctorId2;
        this.patientId = patientId2;
        this.apptId = apptId2;

    }
    private int doctorId;
    private int patientId;
    private int apptId;
    // ... equals and hashCode methods
    public int getDoctorId() {
        return doctorId;
    }
}
