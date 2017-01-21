package com.noveogroup.java.validation;

import com.noveogroup.java.validation.exception.ValidationException;
import java.lang.annotation.Annotation;

/**
 * Validator interface defines a common validation logic
 */
public interface Validator {

    void validate(Object value, Annotation annotation) throws ValidationException;
}
