package ru.academits.service;

public class ContactValidation {
    private boolean valid;
    private String error;

    public ContactValidation() {
        this.valid = true;
    }

    public boolean isValid() {
        return valid;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.valid = false;
        this.error = error;
    }
}
