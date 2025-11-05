package com.pm.patientservice.mapper;

import com.pm.patientservice.dtos.PatientRequestDTO;
import com.pm.patientservice.dtos.PatientResponseDTO;
import com.pm.patientservice.model.Patient;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PatientMapper {

    // Define formatter for dd-MM-yyyy
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public static PatientResponseDTO toDto(Patient patient) {
        PatientResponseDTO patientResponseDTO = new PatientResponseDTO();
        patientResponseDTO.setId(patient.getId());
        patientResponseDTO.setName(patient.getName());
        patientResponseDTO.setEmail(patient.getEmail());
        patientResponseDTO.setAddress(patient.getAddress());
        patientResponseDTO.setDateOfBirth(patient.getDateOfBirth().format(formatter));
        return patientResponseDTO;
    }

    public static Patient toPatientEntity(PatientRequestDTO patientRequestDTO) {
        DateTimeFormatter formDate = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        Patient patient = new Patient();
        patient.setName(patientRequestDTO.getName());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setDateOfBirth(patientRequestDTO.getDateOfBirth());
        patient.setRegistrationDate(patientRequestDTO.getRegistrationDate());

        return  patient;
    }
}
