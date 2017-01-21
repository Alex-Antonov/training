package com.noveogroup.java.validationFactory;

import com.noveogroup.java.util.ThreadActivator;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.noveogroup.java.validation.Validator;
import com.noveogroup.java.validation.exception.ValidationException;
import org.slf4j.LoggerFactory;

/**
 *
 * @author зс
 */
public class ValidationProcess {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ValidationProcess.class);

    //get Object
    public static void Validate(final Object object) throws ValidationException {
        try {

            Class clazz = object.getClass();

            Field[] publicFields = clazz.getDeclaredFields();
            for (Field field : publicFields) {
                field.setAccessible(true);
                Class fieldType = field.getType();
                Annotation[] annos = field.getAnnotations();
                Object obj = field.get(object);
                LOGGER.info("\n------------------------\nName: " + field.getName()
                        + "\nType: " + fieldType.getName()
                        + "\n------------------------");
                for (int i = 0; i < annos.length; i++) {
                    LOGGER.info("Validation Process. Annotation: " + annos[i]);
                    // CR2 now it is OK, but if you generalize the Validator<T> where T is annotation type
                    // you will not need the casting of the annotation in Validator.validate(Object, T) :-)
                    Validator validator = ValidationFactory.getValidatorImplementation(annos[i].annotationType());
                    LOGGER.info("validator: " + validator);
                    validator.validate(obj, annos[i]);
                }

            }

        } catch (IllegalArgumentException ex) {
            Logger.getLogger(ValidationProcess.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ValidationProcess.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
