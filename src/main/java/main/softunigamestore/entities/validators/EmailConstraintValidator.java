package main.softunigamestore.entities.validators;

import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class EmailConstraintValidator implements ConstraintValidator<ValidEmail,String> {
    private static final String EMAIL_REGEXP = "^(?<user>[A-Za-z||\\d]+[.||\\-||_]*[A-Za-z||\\d]+)@(?<host>[A-Za-z|\\-]+[.][A-Za-z|\\-]+[.]*[A-Za-z|\\-]*)$";
    @Override
    public void initialize(ValidEmail constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return false;
    }

    public  boolean isValid(String email){
        return email.matches(EMAIL_REGEXP);
    }

    public String getMessage() {
        return "Invalid Email!";
    }
}