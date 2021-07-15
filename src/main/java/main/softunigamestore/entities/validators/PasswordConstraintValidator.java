package main.softunigamestore.entities.validators;

import javax.validation.ConstraintValidator;

import org.passay.*;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

import static java.lang.String.join;

@Component
public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {
    private String message;

    @Override
    public void initialize(ValidPassword arg0) {
        ConstraintValidator.super.initialize(arg0);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return false;
    }

    public  boolean isValid(String password, String confirmPassword) {

        PasswordValidator validator = new PasswordValidator(Arrays.asList(
                // at least 6 characters
                new LengthRule(6, 30),

                // at least one upper-case character
                new CharacterRule(EnglishCharacterData.UpperCase, 1),

                // at least one lower-case character
                new CharacterRule(EnglishCharacterData.LowerCase, 1),

                // at least one digit character
                new CharacterRule(EnglishCharacterData.Digit, 1),

                // no whitespace
                new WhitespaceRule()

        ));
        RuleResult result = validator.validate(new PasswordData(password));
        if (result.isValid()) {
            if (password.equals(confirmPassword)){
                return true;
            } else {
              validator.getMessages(result).add("Password doesn't match!");
            }
        }
        List<String> messages = validator.getMessages(result);

        this.message = join("\n", messages);
        return false;
    }

    public String getMessage() {
        return message;
    }
}