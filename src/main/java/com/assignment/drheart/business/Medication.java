package com.assignment.drheart.business;

import com.assignment.drheart.entity.Unit;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import lombok.Data;

import javax.validation.constraints.Null;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

//·         description
//o    free text entry
//o    cannot be less then 3 characters
//·         dosage - floating point positive number
//·         unit - can be one of the following values [Grams, Milligrams, Tablet ]
//·         time - time of date at which the medication should be taken
//·         creation date
//o    date at which the medication was created in the system
//o    cannot be in the future
//·         modify date
//o    date at which the medication was updated
//o    cannot be in the future


@Data
public class Medication {

    private Integer id;
    private Integer patientId;
    private String description;
    @Positive
    private Float dosage;
    private Unit unit;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    @JsonSerialize(using = LocalTimeSerializer.class)
    private LocalTime time;
    @PastOrPresent
    private LocalDateTime creationDate;
    @PastOrPresent
    private LocalDateTime modificationDate;
}
