package com.pm.patient_microservice.Mapper;
import com.pm.patient_microservice.dto.PatientRequestDto;
import com.pm.patient_microservice.dto.PatientResponseDto;
import com.pm.patient_microservice.model.Patient;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    PatientResponseDto convertEntityToDto(Patient patient);

    Patient convertDtoToEntity(PatientRequestDto patientRequestDto);
}
