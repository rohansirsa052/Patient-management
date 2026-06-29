package com.pm.patient_microservice.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class PatientResponseDto {
    private UUID id;
    private String name;
    private String email;
    private LocalDate dateOfBirth;
}
