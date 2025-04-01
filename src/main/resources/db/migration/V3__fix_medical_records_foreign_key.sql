-- Drop existing foreign key constraint
ALTER TABLE medical_record DROP FOREIGN KEY FKh52eitcdgeocsfdky93edn4bt;

-- Add new foreign key constraint with correct table reference
ALTER TABLE medical_record ADD CONSTRAINT FK_medical_record_patient FOREIGN KEY (patientid) REFERENCES Patient(id); 