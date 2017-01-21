package com.noveogroup.java.validation.impl;

import com.noveogroup.java.validation.Validator;
import com.noveogroup.java.validation.exception.ValidationException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.noveogroup.java.annotation.StringValue;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import org.slf4j.LoggerFactory;

/**
 *
 * @author зс
 */
public class StringValueValidator implements Validator {
    
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(StringValueValidator.class);
    
    public void validate(final Object targetObject, final Annotation annotation) throws ValidationException {

        StringValue stringValue = (StringValue) annotation;
        

        Pattern pattern = Pattern.compile(stringValue.pattern());
        Matcher match = pattern.matcher((String)targetObject);
        
        if (match.matches()) {
            LOGGER.info("CheakValue.String: " + targetObject.toString() + " pattern match");
        } else {
            throw new ValidationException("StringValueValidator: object is invalid. it's not coincided");
            //System.out.println("CheakValue.String: " + val + " Несовпадение с шаблоном");
        }
    }
}
