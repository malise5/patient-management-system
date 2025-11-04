package com.pm.patientservice.service;

import com.pm.patientservice.dtos.PatientRequestDTO;
import com.pm.patientservice.dtos.PatientResponseDTO;

import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.validation.Valid;


public interface PatientService {

    List<PatientResponseDTO> getAllPatients();

    PatientResponseDTO createNewPatient(@Valid PatientRequestDTO patientRequest);

    List<PatientResponseDTO> getPatientsUnder18();

    PatientResponseDTO updatePatient(@Valid Long id, PatientRequestDTO patientRequest);
}
