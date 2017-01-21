package com.noveogroup.java.validation.impl;

import com.noveogroup.java.validation.Validator;
import com.noveogroup.java.validation.exception.ValidationException;
import java.lang.annotation.Annotation;

public class NotNullValidator implements Validator {

    public void validate(final Object targetObject, final Annotation annotation) throws ValidationException {
        if (targetObject == null) {
            throw new ValidationException("NotNullValidator: object is invalid");
        }
    }
}
