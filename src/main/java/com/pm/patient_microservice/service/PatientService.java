package com.pm.patient_microservice.service;

import com.pm.patient_microservice.Mapper.PatientMapper;
import com.pm.patient_microservice.dto.PatientRequestDto;
import com.pm.patient_microservice.dto.PatientResponseDto;
import com.pm.patient_microservice.model.Patient;
import com.pm.patient_microservice.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {
    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    public PatientService(PatientRepository patientRepository, PatientMapper patientMapper) {
        this.patientRepository = patientRepository;
        this.patientMapper = patientMapper;
    }

    public List<PatientResponseDto> getPatients() {
        return patientRepository.findAll().stream().map((val)-> patientMapper.convertEntityToDto(val)).toList();
    }

    public PatientResponseDto addPatient(PatientRequestDto patientRequestDto) {
        Patient patient = patientMapper.convertDtoToEntity(patientRequestDto);
        Patient savedPatient = patientRepository.save(patient);
        return patientMapper.convertEntityToDto(savedPatient);
    }
}
