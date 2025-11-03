package com.pm.patientservice.service;

import com.pm.patientservice.dtos.PatientRequestDTO;
import com.pm.patientservice.dtos.PatientResponseDTO;
import com.pm.patientservice.mapper.PatientMapper;
import com.pm.patientservice.model.Patient;
import com.pm.patientservice.repository.PatientRepository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;

    public List<PatientResponseDTO> getAllPatients() {
         List<Patient> patients = patientRepository.findAll();

        return patients.stream().map(
                PatientMapper::toDto).toList();
    }

    public PatientResponseDTO createNewPatient(@Valid PatientRequestDTO patientRequest) {
        if (patientRequest == null) {
            return null;
        }

        Patient newPatient = PatientMapper.toPatientEntity(patientRequest);

        return PatientMapper.toDto(patientRepository.save(newPatient));




    }
}
