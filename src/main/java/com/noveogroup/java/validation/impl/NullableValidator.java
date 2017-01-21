package com.noveogroup.java.validation.impl;

import java.lang.annotation.Annotation;

import com.noveogroup.java.validation.Validator;
import com.noveogroup.java.validation.exception.ValidationException;
import org.slf4j.LoggerFactory;

/**
 *
 * @author зс
 */
public class NullableValidator implements Validator {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(NullableValidator.class);

    // CR2 ??
    public void validate(final Object targetObject, final Annotation annotation) throws ValidationException {
        if (targetObject == null) {
            LOGGER.warn("check value for NPE");
        } else {
            LOGGER.info("object has been inited");
        }
    }

}
