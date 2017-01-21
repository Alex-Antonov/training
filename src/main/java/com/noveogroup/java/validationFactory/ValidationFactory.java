package com.noveogroup.java.validationFactory;

import java.lang.annotation.Annotation;
import com.noveogroup.java.validation.Validator;
import java.util.HashMap;
import java.util.Map;
import com.noveogroup.java.annotation.*;
import com.noveogroup.java.validation.impl.*;

/**
 *
 * @author зс
 */
public class ValidationFactory {

    private static final Map<Class<? extends Annotation>, Validator> validation
            = new HashMap<Class<? extends Annotation>, Validator>();

    static {
        validation.put(NotNull.class, new NotNullValidator());
        validation.put(Nullable.class, new NullableValidator());
        validation.put(NotFuture.class, new NotFutureValidator());
        validation.put(IntValue.class, new IntValueValidator());
        validation.put(StringValue.class, new StringValueValidator());
    }

    public static Validator getValidatorImplementation(Class<? extends Annotation> annotation) {
        return validation.get(annotation);
    }

}
