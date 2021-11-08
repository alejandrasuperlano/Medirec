ALTER TABLE medirec.user_roles
DROP FOREIGN KEY FK4m7ds6r5ybh3dnedhh8vjvx5s,
DROP FOREIGN KEY FK7vm8gyi7dj1ovkv2ncp9t7x4e;
ALTER TABLE medirec.user_roles
CHANGE COLUMN patient_id patient_id INT NULL ,
CHANGE doctor_id doctor_id INT NULL ;
ALTER TABLE medirec.user_roles
ADD CONSTRAINT FK4m7ds6r5ybh3dnedhh8vjvx5s
FOREIGN KEY (doctor_id)
REFERENCES medirec.doctor (user_id),
ADD CONSTRAINT FK7vm8gyi7dj1ovkv2ncp9t7x4e
FOREIGN KEY (patient_id)
REFERENCES medirec.patient (user_id);