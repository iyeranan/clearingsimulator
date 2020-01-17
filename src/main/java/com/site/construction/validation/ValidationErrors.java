package com.site.construction.validation;

import java.util.ArrayList;
import java.util.List;

//generic error class for storing free-form validation errors
public class ValidationErrors {
    private List<String> errors;

    public List<String> getErrors() {
        if (errors == null) {
            this.errors = new ArrayList<>();
        }
        return this.errors;
    }
}
