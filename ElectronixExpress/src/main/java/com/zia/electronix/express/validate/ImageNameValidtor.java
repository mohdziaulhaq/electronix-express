package com.zia.electronix.express.validate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageNameValidtor implements ConstraintValidator<ImageNameValid, String> {

    private Logger logger = LoggerFactory.getLogger(ImageNameValidtor.class);
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if(value.isBlank())
            return false;
        else
            return true;
    }
}
