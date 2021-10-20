package com.assignment.drheart.service;

import com.assignment.drheart.business.Medication;
import com.assignment.drheart.config.MapperUtil;
import com.assignment.drheart.entity.MedicationEntity;
import com.assignment.drheart.repository.MedicationRepository;
import com.assignment.drheart.repository.MedicationRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicationService {

    private MedicationRepository medicationRepository;
    private MapperUtil mapper;

    public MedicationService(MedicationRepository medicationRepository, MapperUtil mapper){
        this.medicationRepository = medicationRepository;
        this.mapper = mapper;
    }

    public Medication getMedicationById(Integer id){
        return mapper.map(medicationRepository.getById(id), Medication.class);
    }

    public List<Medication> getAllMedications(){
        List<MedicationEntity> medicationEntities = medicationRepository.findAll();

        if (CollectionUtils.isEmpty(medicationEntities)){
            throw new EntityNotFoundException();
        }
        return medicationEntities.stream()
                .map(medicationEntity -> mapper.map(medicationEntity, Medication.class))
                .collect(Collectors.toList());
    }

    public Medication createMedication(Medication medication){
        MedicationEntity medicationEntity = mapper.map(medication,MedicationEntity.class);
        medicationEntity.setCreationDate(LocalDateTime.now());
        medicationEntity.setModificationDate(LocalDateTime.now());

        MedicationEntity savedMedication = medicationRepository.save(medicationEntity);
        return mapper.map(savedMedication,Medication.class);
    }

    public Medication updateMedication(Medication medication){
        MedicationEntity medicationEntity = mapper.map(medication,MedicationEntity.class);
        medicationEntity.setModificationDate(LocalDateTime.now());

        MedicationEntity updatedMedicationEntity = medicationRepository.save(medicationEntity);
        return mapper.map(updatedMedicationEntity,Medication.class);
    }

    public void deleteMedicationById(Integer id){
        medicationRepository.deleteById(id);
    }

}
