package com.assignment.drheart.service;

import com.assignment.drheart.business.Patient;
import com.assignment.drheart.config.MapperUtil;
import com.assignment.drheart.entity.PatientEntity;
import com.assignment.drheart.repository.PatientRepository;
import com.github.dozermapper.core.Mapper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientService {

    private PatientRepository patientRepository;
    private MapperUtil mapper;

    public PatientService(PatientRepository patientRepository, MapperUtil mapper){
        this.patientRepository = patientRepository;
        this.mapper = mapper;
    }

    public Patient getPatientById(Integer id){
        return mapper.map(patientRepository.getById(id), Patient.class);
    }

    public List<Patient> getAllPatients(){
        List<PatientEntity> patientEntities = patientRepository.findAll();

        if (CollectionUtils.isEmpty(patientEntities)){
            throw new EntityNotFoundException();
        }
        return patientEntities.stream()
                .map(patientEntity -> mapper.map(patientEntity, Patient.class))
                .collect(Collectors.toList());
    }

    public Patient createPatient(Patient patient){
        PatientEntity patientEntity = mapper.map(patient,PatientEntity.class);
        patientEntity.setCreationDate(LocalDateTime.now());
        patientEntity.setModificationDate(LocalDateTime.now());

        PatientEntity savedPatient = patientRepository.save(patientEntity);
        return mapper.map(savedPatient,Patient.class);
    }

    public Patient updatePatient(Patient patient){
        PatientEntity patientEntity = mapper.map(patient,PatientEntity.class);
        patientEntity.setModificationDate(LocalDateTime.now());

        PatientEntity updatedPatientEntity = patientRepository.save(patientEntity);
        return mapper.map(updatedPatientEntity,Patient.class);
    }

    public void deletePatientById(Integer id){
        patientRepository.deleteById(id);
    }

}
