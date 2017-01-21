package com.noveogroup.java.validation.impl;

import com.noveogroup.java.validation.Validator;
import com.noveogroup.java.validation.exception.ValidationException;
import java.lang.annotation.Annotation;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author зс
 */
public class NotFutureValidator implements Validator {
    //private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(IntValueValidator.class);

    public void validate(final Object targetObject, final Annotation annotation) throws ValidationException {

        //System.out.println("CheakValue.Date: " + date);
        if (((Date) targetObject).compareTo((Date) java.util.Calendar.getInstance().getTime()) > 0) {
            throw new ValidationException("NotFutureValidator: object is invalid");
        }

    }
}
