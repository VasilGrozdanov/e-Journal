package nbu.bg.electronicjournal.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.Set;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = ValidationException.class)
    public String constraintViolationHandler(HttpServletRequest req, Model model,
            ConstraintViolationException exception) {

        StringBuilder errorMsg = new StringBuilder();
        Set<ConstraintViolation<?>> constraintViolations = exception.getConstraintViolations();

        if (constraintViolations == null) {
            log.error("PasswordMatchesValidator throw this exception !");
            errorMsg.append("Password mismatch!");
        } else {
            for (ConstraintViolation cv : constraintViolations) {
                errorMsg.append(cv.getMessageTemplate() + " ");
            }
        }
        log.error(errorMsg.toString());
        model.addAttribute("errorMessage", errorMsg);
        return "register";
    }

    @ExceptionHandler(InvalidRegistration.class)
    public String handleInvalidRegistration(final InvalidRegistration exception) {
        log.error(exception.getMessage());
        return "redirect:/register";
    }

    //Added exception handling for the Company
    @ExceptionHandler(TeacherNotQualifiedException.class)
    public String handleCompanyNotFound(final TeacherNotQualifiedException exception) {
        log.error(exception.getMessage());
        return "redirect:/company";
    }

    @ExceptionHandler(Exception.class)
    public String genericExceptionHandler(Exception exception, Model model) {
        log.error(exception.getMessage());
        model.addAttribute("errorMessage", "Something went wrong!");
        return "error";
    }
}
