package com.pm.patientservice.customExceptions;

public class InvalidDateOfBirthException extends ApiException{
    public InvalidDateOfBirthException(String message) {
        super(message);
    }
}
