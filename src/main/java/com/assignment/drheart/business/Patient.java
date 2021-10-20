package com.assignment.drheart.business;

import com.assignment.drheart.entity.Gender;
import com.assignment.drheart.entity.MedicationEntity;
import com.assignment.drheart.util.Over18;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.PastOrPresent;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Data
public class Patient {

    private Integer id;
    @NotBlank(message = "First name should not be blank")
    private String firstName;
    @NotBlank(message = "Last name should not be blank")
    private String lastName;
    @NotNull(message = "FEMALE/MALE/DIVERS")
    private Gender gender;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @PastOrPresent
    @Over18
    private LocalDate dob;
    @PastOrPresent
    private LocalDateTime creationDate;
    @PastOrPresent
    private LocalDateTime modificationDate;
}
