package com.assignment.drheart.repository;

import com.assignment.drheart.entity.MedicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicationRepository extends JpaRepository<MedicationEntity, Integer> {

    List<MedicationEntity> getByPatientId(Integer patientId);
}
