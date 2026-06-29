package com.pm.patient_microservice.controller;

import com.pm.patient_microservice.dto.PatientRequestDto;
import com.pm.patient_microservice.dto.PatientResponseDto;
import com.pm.patient_microservice.service.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients")
public class PatientController {
    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }
    @GetMapping("")
    public ResponseEntity <List<PatientResponseDto>> getAllPatients(){
        List<PatientResponseDto> fetchedPatients = patientService.getPatients();
        return new ResponseEntity<>(fetchedPatients, HttpStatus.OK);
    }
    @PostMapping("/savePatient")
    public ResponseEntity<PatientResponseDto> savePatient(@RequestBody PatientRequestDto patientRequestDto){
        PatientResponseDto savedPatient = patientService.addPatient(patientRequestDto);
        return new ResponseEntity<>(savedPatient, HttpStatus.CREATED);
    }
}
