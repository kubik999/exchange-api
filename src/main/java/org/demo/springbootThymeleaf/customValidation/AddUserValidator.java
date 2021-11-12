package org.demo.springbootThymeleaf.customValidation;

import org.demo.springbootThymeleaf.entity.User;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ValidationUtils;

import java.util.Locale;

@Controller
public class AddUserValidator extends ValidationUtils {

    public static boolean customValidateAddUser(User theUser, Model theModel, BindingResult result, Locale locale, MessageSource messageSource) {

        AddUserValidator.rejectIfEmailAndPhoneIsEmpty(theUser, result, locale, theModel, messageSource);

        if (result.hasErrors()) {
            return true;
        } else {
            return false;
        }
    }

    public static void rejectIfEmailAndPhoneIsEmpty(User theUser, BindingResult result, Locale locale, Model theModel, MessageSource messageSource) {

        if ((theUser.getStanKonta() == null && theUser.getPesel() == null)) {

            FieldError tooLongTeamName = new FieldError("user", "emailAndPhoneIsEmpty",
                    messageSource.getMessage("error.emailAndPhoneIsEmpty", null, locale));
            result.addError(tooLongTeamName);
            theModel.addAttribute("emailAndPhoneIsEmpty", messageSource.getMessage("error.emailAndPhoneIsEmpty", null, locale));
        }
    }
}
