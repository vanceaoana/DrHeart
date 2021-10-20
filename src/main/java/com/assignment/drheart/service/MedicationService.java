package com.assignment.drheart.service;

import com.assignment.drheart.business.Medication;
import com.assignment.drheart.entity.MedicationEntity;
import com.assignment.drheart.repository.MedicationRepository;
import com.assignment.drheart.util.MapperUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicationService {

    private MedicationRepository medicationRepository;
    private MapperUtil mapper;

    public MedicationService(MedicationRepository medicationRepository, MapperUtil mapper) {
        this.medicationRepository = medicationRepository;
        this.mapper = mapper;
    }

    public List<Medication> getMedicationByPatientId(Integer id) {
        return mapper.mapList(medicationRepository.getByPatientId(id), Medication.class);
    }

    @Transactional
    public List<Medication> createMedication(Integer patientId, List<Medication> medicationList) {

        List<MedicationEntity> medicationEntityList = medicationList.stream()
                .map(medication -> {
                    MedicationEntity medicationEntity = mapper.map(medication, MedicationEntity.class);
                    medicationEntity.setCreationDate(LocalDateTime.now());
                    medicationEntity.setModificationDate(LocalDateTime.now());
                    medicationEntity.setPatientId(patientId);

                    return medicationEntity;
                })
                .collect(Collectors.toList());

        List<MedicationEntity> savedMedication = medicationRepository.saveAll(medicationEntityList);
        return mapper.mapList(savedMedication, Medication.class);
    }

    public Medication updateMedication(Integer patientId, Medication medication) {
        MedicationEntity medicationEntity = mapper.map(medication, MedicationEntity.class);
        medicationEntity.setModificationDate(LocalDateTime.now());
        medicationEntity.setPatientId(patientId);

        boolean exists = medicationRepository.existsById(medicationEntity.getId());
        if (!exists) {
            throw new EntityNotFoundException();
        }

        MedicationEntity updatedMedicationEntity = medicationRepository.save(medicationEntity);
        return mapper.map(updatedMedicationEntity, Medication.class);
    }

    public void deleteMedicationById(Integer id) {
        medicationRepository.deleteById(id);
    }

}
