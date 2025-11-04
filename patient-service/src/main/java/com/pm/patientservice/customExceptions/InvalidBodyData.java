package com.pm.patientservice.customExceptions;

public class InvalidBodyData extends ApiException{
    public InvalidBodyData(String message) {
        super(message);
    }
}
