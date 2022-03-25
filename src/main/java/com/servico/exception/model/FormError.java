package com.servico.exception.model;

public class FormError {

    private final String field;
    private final String message;

    public FormError(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }
}
