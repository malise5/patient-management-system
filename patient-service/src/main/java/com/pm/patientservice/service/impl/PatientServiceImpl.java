package com.pm.patientservice.service.impl;

import com.pm.patientservice.customExceptions.EmailAlreadyExistsException;
import com.pm.patientservice.customExceptions.InvalidBodyData;
import com.pm.patientservice.customExceptions.InvalidDateOfBirthException;
import com.pm.patientservice.customExceptions.PatientNotFoundException;
import com.pm.patientservice.dtos.PatientRequestDTO;
import com.pm.patientservice.dtos.PatientResponseDTO;
import com.pm.patientservice.grpc.BillingServiceGrpcClient;
import com.pm.patientservice.kafka.KafkaProducer;
import com.pm.patientservice.mapper.PatientMapper;
import com.pm.patientservice.model.Patient;
import com.pm.patientservice.repository.PatientRepository;
import com.pm.patientservice.service.PatientService;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final BillingServiceGrpcClient billingServiceGrpcClient;
    private final KafkaProducer kafkaProducerClient;

    private final KafkaProducer kafkaProducer;

    // Define formatter for dd-MM-yyyy
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @Override
    public List<PatientResponseDTO> getAllPatients() {
         List<Patient> patients = patientRepository.findAll();

        return patients.stream().map(
                PatientMapper::toDto).toList();
    }

    @Override
    public PatientResponseDTO createNewPatient(@Valid PatientRequestDTO patientRequest) {
        if (patientRequest == null) {
            throw new InvalidBodyData("Patient request cannot be null");
        }


        if (patientRepository.existsByEmail(patientRequest.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists " + patientRequest.getEmail());
        }

        if (Period.between(patientRequest.getDateOfBirth(), LocalDate.now()).getYears() < 0) {
            throw new InvalidDateOfBirthException("Date of birth cannot be in the future");
        }


        Patient newPatient = PatientMapper.toPatientEntity(patientRequest);

        Patient savePatient = patientRepository.save(newPatient);



        billingServiceGrpcClient.createBillingAccount(savePatient.getId().toString(), savePatient.getName(), savePatient.getEmail());

        kafkaProducer.sendEvent(savePatient);

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

    @Override
    public PatientResponseDTO updatePatient(Long id, PatientRequestDTO patientRequest) {
        Patient updatedPatient = patientRepository.findById(id).orElseThrow(
                ()-> new PatientNotFoundException("Patient not found with ID " + id));

        if (patientRepository.existsByEmailAndIdNot(patientRequest.getEmail(), id)) {
            throw new EmailAlreadyExistsException("Email already exists " + patientRequest.getEmail());
        }
        updatedPatient.setName(patientRequest.getName());
        updatedPatient.setEmail(patientRequest.getEmail());
        updatedPatient.setAddress(patientRequest.getAddress());
        updatedPatient.setDateOfBirth(patientRequest.getDateOfBirth());
        updatedPatient.setRegistrationDate(patientRequest.getRegistrationDate());

        Patient savedPatient = patientRepository.save(updatedPatient);

        return PatientMapper.toDto(savedPatient);


    }

    @Override
    public void deletePatient(Long patientId) {

        Patient patient = patientRepository.findById(patientId).orElseThrow(
                () -> new PatientNotFoundException("Patient not found with ID " + patientId)
        );

        patientRepository.delete(patient);

    }
}
