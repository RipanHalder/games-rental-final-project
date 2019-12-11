package com.ripanhalder.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanWrapperImpl;

public class MatchingFieldsValidator implements ConstraintValidator<MatchingFields, Object> {
	
	private String firstFN;
    private String secondFN;
    private String msg;

    @Override
    public void initialize(final MatchingFields constraintAnnotation) {
    	firstFN = constraintAnnotation.first();
    	secondFN = constraintAnnotation.second();
    	msg = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        boolean valid = true;
        try
        {
            final Object ObjectNo1 = new BeanWrapperImpl(value).getPropertyValue(firstFN);
            final Object ObjectNo2 = new BeanWrapperImpl(value).getPropertyValue(secondFN);

            valid =  ObjectNo1 == null && ObjectNo2 == null || ObjectNo1 != null && ObjectNo1.equals(ObjectNo2);
        }
        catch (final Exception ignore){}

        if (!valid){
            context.buildConstraintViolationWithTemplate(msg)
                    .addPropertyNode(firstFN)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }

        return valid;
    }
	
}