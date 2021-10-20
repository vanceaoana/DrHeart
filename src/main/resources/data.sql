DROP TABLE IF EXISTS Patient;
DROP TABLE IF EXISTS Medication;

CREATE TABLE Patient (
                              id INT AUTO_INCREMENT  PRIMARY KEY,
                              first_name VARCHAR(250) NOT NULL,
                              last_name VARCHAR(250) NOT NULL,
                              gender VARCHAR(250) NOT NULL,
                              date_of_birth DATE NOT NULL,
                              creation_date DATE NOT NULL,
                              modification_date DATE NOT NULL
);

CREATE TABLE Medication (
                         id INT AUTO_INCREMENT  PRIMARY KEY,
                         patient_id INT NOT NULL,
                         description VARCHAR(250) NOT NULL,
                         dosage FLOAT NOT NULL,
                         unit VARCHAR NOT NULL,
                         time TIMESTAMP NOT NULL,
                         creation_date DATE NOT NULL,
                         modification_date DATE NOT NULL
);
