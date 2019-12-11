package com.ripanhalder.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

//This class validates the email with written pattern and final matcher
public class ValidatorForEmail implements ConstraintValidator<CheckForValidEmail, String> {

	private Pattern patternObject;
	private Matcher matcherObject;
	private static final String THE_EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	@Override
	public boolean isValid(final String email, final ConstraintValidatorContext context) {
		patternObject = Pattern.compile(THE_EMAIL_PATTERN);
		if (email == null) {
			return false;
		}
		matcherObject = patternObject.matcher(email);
		return matcherObject.matches();
	}

}