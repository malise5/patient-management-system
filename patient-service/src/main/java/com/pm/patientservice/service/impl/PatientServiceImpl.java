package com.pm.patientservice.service.impl;

import com.pm.patientservice.dtos.PatientRequestDTO;
import com.pm.patientservice.dtos.PatientResponseDTO;
import com.pm.patientservice.mapper.PatientMapper;
import com.pm.patientservice.model.Patient;
import com.pm.patientservice.repository.PatientRepository;
import com.pm.patientservice.service.PatientService;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    @Override
    public List<PatientResponseDTO> getAllPatients() {
         List<Patient> patients = patientRepository.findAll();

        return patients.stream().map(
                PatientMapper::toDto).toList();
    }

    @Override
    public PatientResponseDTO createNewPatient(@Valid PatientRequestDTO patientRequest) {
        if (patientRequest == null) {
            throw new IllegalArgumentException("Patient request cannot be null");
        }

        Patient newPatient = PatientMapper.toPatientEntity(patientRequest);

        Patient savePatient = patientRepository.save(newPatient);

        return PatientMapper.toDto(savePatient);

    }

    @Override
    public List<PatientResponseDTO> getPatientsUnder18() {
        List<Patient> under18Patient = patientRepository.findAll();

        return under18Patient
                .stream()
                .filter(patient -> patient.getDateOfBirth() != null)
                .filter(patient -> Period.between(patient.getDateOfBirth(), LocalDate.now()).getYears() < 18)
                .map(PatientMapper::toDto)
                .toList();
    }
}
