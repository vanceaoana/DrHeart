package com.assignment.drheart.business;

import lombok.Data;

import java.util.List;

@Data
public class PatientFull extends Patient {

    private List<Medication> medicationList;
}
