-- Drop existing foreign key constraints
ALTER TABLE health_goals DROP FOREIGN KEY FKh52eitcdgeocsfdky93edn4br;
ALTER TABLE health_goals DROP FOREIGN KEY FKh52eitcdgeocsfdky93edn4bs;

-- Add new foreign key constraints with correct table references
ALTER TABLE health_goals ADD CONSTRAINT FK_health_goals_doctor FOREIGN KEY (doctor_id) REFERENCES Doctor(id);
ALTER TABLE health_goals ADD CONSTRAINT FK_health_goals_patient FOREIGN KEY (patient_id) REFERENCES Patient(id); 