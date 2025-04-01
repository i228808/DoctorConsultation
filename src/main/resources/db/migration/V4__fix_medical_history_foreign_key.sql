-- Drop existing foreign key constraint if it exists
ALTER TABLE medical_history DROP FOREIGN KEY IF EXISTS FKh52eitcdgeocsfdky93edn4bu;

-- Add new foreign key constraint with correct table reference
ALTER TABLE medical_history ADD CONSTRAINT FK_medical_history_patient FOREIGN KEY (patient_id) REFERENCES Patient(id); 