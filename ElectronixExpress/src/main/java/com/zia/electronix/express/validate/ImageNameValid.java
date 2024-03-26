package com.zia.electronix.express.validate;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented()
@Constraint(validatedBy = ImageNameValidtor.class)
public @interface ImageNameValid {

    //errpr message
    String message() default "Invalid Image Name";

    //represent group of constraints
    Class<?>[] groups() default {};

    //additional information about annotation
    Class<? extends Payload>[] payload() default{ };
}
