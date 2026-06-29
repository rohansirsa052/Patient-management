package com.pm.patient_microservice.repository;

import com.pm.patient_microservice.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PatientRepository extends JpaRepository <Patient, UUID> {

}
