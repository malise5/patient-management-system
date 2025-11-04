package com.pm.patientservice.customExceptions;

public class PatientNotFoundException extends ApiException{
    public PatientNotFoundException(String message) {
        super(message);
    }
}
