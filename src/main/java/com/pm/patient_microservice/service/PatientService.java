package com.pm.patient_microservice.service;

import com.pm.patient_microservice.Mapper.PatientMapper;
import com.pm.patient_microservice.dto.PatientRequestDto;
import com.pm.patient_microservice.dto.PatientResponseDto;
import com.pm.patient_microservice.exception.PatientNotFoundException;
import com.pm.patient_microservice.model.Patient;
import com.pm.patient_microservice.repository.PatientRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    public PatientResponseDto updatePatient(@Valid UUID id, @Valid PatientRequestDto patientRequestDto) {
        Optional <Patient> savedPatient = patientRepository.findById(id);
        if(savedPatient.isEmpty()) throw new PatientNotFoundException("Patient not found " + id);
        Patient patient = savedPatient.get();
        patient.setName(patientRequestDto.getName());
        patient.setAddress(patientRequestDto.getAddress());
        patient.setEmail(patientRequestDto.getEmail());
        patient.setDateOfBirth(patientRequestDto.getDateOfBirth());

        Patient updatedPatient = patientRepository.save(patient);

        return patientMapper.convertEntityToDto(updatedPatient);
    }

    public String deletePatient(UUID id) {
        Optional <Patient> savedPatient = patientRepository.findById(id);
        if(savedPatient.isEmpty()) throw new PatientNotFoundException("Patient not found " + id);
        Patient patient = savedPatient.get();
        patientRepository.delete(patient);
        return "Patient Deleted";
    }
}
