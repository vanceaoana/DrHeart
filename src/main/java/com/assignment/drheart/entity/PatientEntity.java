package com.assignment.drheart.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

//·         first name - free text entry
//·         last name - free text entry
//·         gender - Male / Female / Divers
//·         date of birth
//o    cannot be in the future
//o    the patient cannot be less then 18 years when registered
//·         creation date
//o    date at which the patient was added in the system
//o    cannot be in the future
//·         modify date
//o    date at which the patient was updated
//o    cannot be in the future

@Data
@Entity
@Table(name = "Patient")
public class PatientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column(name = "date_of_birth")
    private LocalDate dob;
    @Column(name = "creation_date")
    private LocalDateTime creationDate;
    @Column(name = "modification_date")
    private LocalDateTime modificationDate;

    @OneToMany(mappedBy = "patientId", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<MedicationEntity> medicationEntityList;

}
