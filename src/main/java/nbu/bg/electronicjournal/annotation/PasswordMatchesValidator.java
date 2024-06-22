package nbu.bg.electronicjournal.annotation;

import nbu.bg.electronicjournal.model.dto.UserRegisterDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintViolationException;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        UserRegisterDto user = (UserRegisterDto) obj;
        boolean equals = user.getPassword().equals(user.getMatchingPassword());
        if (!equals) {
            throw new ConstraintViolationException(
                    "Password " + user.getPassword() + " is different from " + user.getMatchingPassword(), null);
        }
        return equals;
    }
}