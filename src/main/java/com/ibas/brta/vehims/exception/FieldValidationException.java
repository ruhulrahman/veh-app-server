package com.ibas.brta.vehims.exception;

import java.util.Map;

public class FieldValidationException extends RuntimeException {
    private final Map<String, String> fieldErrors;

    public FieldValidationException(Map<String, String> fieldErrors) {
        super("Field validation failed");
        this.fieldErrors = fieldErrors;
    }

    public Map<String, String> getFieldErrors() {
        return fieldErrors;
    }
}