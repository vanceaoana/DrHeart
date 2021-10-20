package com.assignment.drheart.service;

import com.assignment.drheart.business.Patient;
import com.assignment.drheart.business.PatientFull;
import com.assignment.drheart.entity.PatientEntity;
import com.assignment.drheart.repository.PatientRepository;
import com.assignment.drheart.util.MapperUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientService {

    private PatientRepository patientRepository;
    private MapperUtil mapper;

    public PatientService(PatientRepository patientRepository, MapperUtil mapper) {
        this.patientRepository = patientRepository;
        this.mapper = mapper;
    }

    public PatientFull getPatientById(Integer id) {
        return mapper.map(patientRepository.getById(id), PatientFull.class);
    }

    public List<Patient> getAllPatients(String sortBy, String query) {
        List<PatientEntity> patientEntities = null;
        if (!StringUtils.isEmpty(query)) {
            patientEntities = patientRepository.findByLastNameContainsOrFirstNameContains(query, query);
        } else {
            if ("firstName".equals(sortBy)) {

                patientEntities = patientRepository.findByOrderByFirstNameAsc();
            } else {
                patientEntities = patientRepository.findByOrderByLastNameAsc();
            }
        }

        return patientEntities.stream()
                .map(patientEntity -> mapper.map(patientEntity, Patient.class))
                .collect(Collectors.toList());
    }

    public Patient createPatient(Patient patient) {
        PatientEntity patientEntity = mapper.map(patient, PatientEntity.class);
        patientEntity.setCreationDate(LocalDateTime.now());
        patientEntity.setModificationDate(LocalDateTime.now());

        PatientEntity savedPatient = patientRepository.save(patientEntity);
        return mapper.map(savedPatient, Patient.class);
    }

    public Patient updatePatient(Patient patient) {
        PatientEntity patientEntity = mapper.map(patient, PatientEntity.class);
        patientEntity.setModificationDate(LocalDateTime.now());

        PatientEntity updatedPatientEntity = patientRepository.save(patientEntity);
        return mapper.map(updatedPatientEntity, Patient.class);
    }

    public void deletePatientById(Integer id) {
        patientRepository.deleteById(id);
    }

    public boolean existsById(Integer id) {
        return patientRepository.existsById(id);
    }


}
