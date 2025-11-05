package com.pm.patientservice.controller;

import com.pm.patientservice.dtos.PatientRequestDTO;
import com.pm.patientservice.dtos.PatientResponseDTO;
import com.pm.patientservice.service.PatientService;
import com.pm.patientservice.validators.CreatePatientValidationGroups;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/patients")
@Tag(name = "Patient", description = "API for managing patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @GetMapping
    @Operation(summary = "Get Patients")
    public ResponseEntity<List<PatientResponseDTO>> getAllPatient(){
        List<PatientResponseDTO> patients =  patientService.getAllPatients();

        return ResponseEntity.ok().body(patients);
    }

    @GetMapping("/under18Years")
    @Operation(summary = "Get Patients under 18 years of Age")
    public ResponseEntity<List<PatientResponseDTO>> getUnder18Years(){
        List<PatientResponseDTO> under18Years = patientService.getPatientsUnder18();

        return ResponseEntity.ok().body(under18Years);
    }

    @PostMapping
    @Operation(summary = "Create a new Patients")
    public ResponseEntity<PatientResponseDTO> createPatient(@Validated({Default.class, CreatePatientValidationGroups.class}) @RequestBody PatientRequestDTO patientRequest){

        PatientResponseDTO newPatient = patientService.createNewPatient(patientRequest);

        return new ResponseEntity<>(newPatient, HttpStatus.CREATED);
    }

    @PutMapping("/{patientId}")
    @Operation(summary = "Update a Patients")
    public ResponseEntity<PatientResponseDTO> updatePatient(@Validated({Default.class}) @RequestBody PatientRequestDTO patientRequest, @PathVariable Long patientId){
        PatientResponseDTO updatePatient = patientService.updatePatient(patientId, patientRequest);

        return new ResponseEntity<>(updatePatient, HttpStatus.OK);
    }

    @DeleteMapping("/{patientId}")
    @Operation(summary = "Delete a Patients")
    public ResponseEntity<Void> deletePatient(@PathVariable Long patientId){

        patientService.deletePatient(patientId);
        return ResponseEntity.noContent().build();
    }
}
