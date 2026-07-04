package com.pm.patient_microservice.controller;

import com.pm.patient_microservice.dto.PatientRequestDto;
import com.pm.patient_microservice.dto.PatientResponseDto;
import com.pm.patient_microservice.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/patients")
@Tag(name = "Patient APIs", description = "Operations related to patient management")
public class PatientController {
    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }
    @GetMapping("")
    @Operation(
            summary = "Get all patients",
            description = "Fetches the complete list of patients."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Patients retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity <List<PatientResponseDto>> getAllPatients(){
        List<PatientResponseDto> fetchedPatients = patientService.getPatients();
        return new ResponseEntity<>(fetchedPatients, HttpStatus.OK);
    }

    @PostMapping("/savePatient")
    @Operation(
            summary = "Create a new patient",
            description = "Creates a new patient record in the system."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Patient created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid patient data"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<PatientResponseDto> createPatient(@Valid @RequestBody PatientRequestDto patientRequestDto){
        PatientResponseDto savedPatient = patientService.addPatient(patientRequestDto);
        return new ResponseEntity<>(savedPatient, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update an existing patient",
            description = "Update an existing patient record in the system."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Patient updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid patient data"),
            @ApiResponse(responseCode = "404", description = "Patient not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<PatientResponseDto> updatePatient(@PathVariable UUID id, @Valid @RequestBody PatientRequestDto patientRequestDto){
        PatientResponseDto responseDto = patientService.updatePatient(id, patientRequestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Update patient",
            description = "Updates the details of an existing patient."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Patient updated successfully"),
            @ApiResponse(responseCode = "404", description = "Patient not found"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    public ResponseEntity<String> deletePatient(@PathVariable UUID id){
        String result = patientService.deletePatient(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
