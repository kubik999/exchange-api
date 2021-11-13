package pl.app.user;

import lombok.AllArgsConstructor;
import pl.app.entity.AppUser;
import pl.app.util.AppConstant;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ValidationUtils;

import java.time.LocalDate;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

@Component
@AllArgsConstructor
class UserValidator extends ValidationUtils {

    private UserService userService;
    private MessageSource messageSource;

    public void customValidateAddUser(AppUser user, Model theModel, BindingResult result, Locale locale) {
        validateAge(user, result, locale, theModel);
        validateExistingUser(user, result, locale, theModel);
    }

    private void validateExistingUser(AppUser user, BindingResult result, Locale locale, Model model) {

        Optional<AppUser> persistedUser = userService.findByPesel(user.getPesel());
        if (persistedUser.isPresent()) {
            FieldError userAlreadyExist = new FieldError("appUser", "userAlreadyExist",
                    messageSource.getMessage("error.userAlreadyExist", null, locale));
            result.addError(userAlreadyExist);
            model.addAttribute("userAlreadyExist", messageSource.getMessage("error.userAlreadyExist", null, locale));
        }
    }

    private void validateAge(AppUser user, BindingResult result, Locale locale, Model model) {
        LocalDate userBirthDate = user.getBirthDate();
        LocalDate minUserAge = LocalDate.now().minusYears(AppConstant.Dictionary.ADULT_MIN_AGE);
        if (Objects.nonNull(userBirthDate) && userBirthDate.isAfter(minUserAge)) {
            FieldError underagePerson = new FieldError("appUser", "underagePerson",
                    messageSource.getMessage("error.underagePerson", null, locale));
            result.addError(underagePerson);
            model.addAttribute("underagePerson", messageSource.getMessage("error.underagePerson", null, locale));
        }
    }
}
