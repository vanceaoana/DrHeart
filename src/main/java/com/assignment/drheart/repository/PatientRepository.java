package com.assignment.drheart.repository;

import com.assignment.drheart.entity.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<PatientEntity, Integer> {

    List<PatientEntity> findByOrderByFirstNameAsc();

    List<PatientEntity> findByOrderByLastNameAsc();

    List<PatientEntity> findByLastNameContainsOrFirstNameContains(String queryLastName,String queryFirstName);
}
