package com.zia.electronix.express.validate;

import jakarta.validation.Constraint;

import java.lang.annotation.*;

@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented()
@Constraint(validatedBy = ImageNameValidtor.class)
public @interface ImageNameValid {
}
