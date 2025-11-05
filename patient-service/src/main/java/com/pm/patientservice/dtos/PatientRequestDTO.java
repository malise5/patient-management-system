package com.pm.patientservice.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pm.patientservice.validators.CreatePatientValidationGroups;
import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientRequestDTO {
    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name is required")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Address is required")
    private String address;

    @NotNull(message = "Date of birth is required")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Schema(type = "string", pattern = "dd-MM-yyyy", example = "05-11-2025")
    private LocalDate dateOfBirth;

    @NotNull(groups = CreatePatientValidationGroups.class, message = "Registration date is required")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Schema(type = "string", pattern = "dd-MM-yyyy", example = "05-11-2025")
    private LocalDate registrationDate;

}