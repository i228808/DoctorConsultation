-- Drop the existing foreign key constraint
ALTER TABLE prescription DROP FOREIGN KEY FKh52eitcdgeocsfdky93edn4br;

-- Add the new foreign key constraint with the correct table name
ALTER TABLE prescription ADD CONSTRAINT FK_prescription_patient FOREIGN KEY (patient_id) REFERENCES Patient(id); 