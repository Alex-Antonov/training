package com.noveogroup.java.validation.impl;

import java.lang.annotation.Annotation;

import com.noveogroup.java.annotation.IntValue;
import com.noveogroup.java.util.ThreadActivator;
import com.noveogroup.java.validation.Validator;
import com.noveogroup.java.validation.exception.ValidationException;
import org.slf4j.LoggerFactory;

/**
 *
 * @author зс
 */
public class IntValueValidator implements Validator {
// CR2 with generics it can looks like
// public class IntValueValidator implements Validator<IntValue>
//   public void validate(final Object targetObject, final IntValue annotation) throws ValidationException {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(IntValueValidator.class);

    public void validate(final Object targetObject, final Annotation annotation) throws ValidationException {
        int field = (Integer) targetObject;

        // CR2 Here can be improved with generics
        IntValue intValue = (IntValue) annotation;

        if (field >= intValue.min() && field <= intValue.max()) {
            // CR2 Only english, please
            LOGGER.info("CheakValue.String: " + targetObject + " pattern match");
        } else {
            throw new ValidationException("IntValueValidator: object is invalid. it's not coincided");
            //System.out.println("CheakValue.String: " + val + " Несовпадение с шаблоном");
        }
    }
}
