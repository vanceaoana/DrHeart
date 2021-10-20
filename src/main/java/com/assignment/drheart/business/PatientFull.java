package com.assignment.drheart.business;

import com.assignment.drheart.entity.MedicationEntity;
import lombok.Data;

import java.util.List;

@Data
public class PatientFull extends Patient {

    private List<MedicationEntity> medicationEntityList;
}
