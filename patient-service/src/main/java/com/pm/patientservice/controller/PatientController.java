package com.pm.patientservice.controller;

import com.pm.patientservice.dtos.PatientRequestDTO;
import com.pm.patientservice.dtos.PatientResponseDTO;
import com.pm.patientservice.service.PatientService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @GetMapping
    public ResponseEntity<List<PatientResponseDTO>> getAllPatient(){
        List<PatientResponseDTO> patients =  patientService.getAllPatients();

        return ResponseEntity.ok().body(patients);
    }

    @GetMapping("/under18Years")
    public ResponseEntity<List<PatientResponseDTO>> getUnder18Years(){
        List<PatientResponseDTO> under18Years = patientService.getPatientsUnder18();

        return ResponseEntity.ok().body(under18Years);
    }

    @PostMapping
    public ResponseEntity<PatientResponseDTO> createPatient(@Valid @RequestBody PatientRequestDTO patientRequest){

        PatientResponseDTO newPatient = patientService.createNewPatient(patientRequest);

        return new ResponseEntity<>(newPatient, HttpStatus.CREATED);
    }
}
