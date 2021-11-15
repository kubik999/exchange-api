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
import pl.app.util.PeselValidator;

import java.time.LocalDate;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

@Component
@AllArgsConstructor
class UserValidator extends ValidationUtils {

    private UserService userService;
    private MessageSource messageSource;

    public Optional<BindingResult> validate(AppUserDto user, Model model, BindingResult result) {
        validateAge(user, result, model);
        validateExistingUser(user, result, model);
        validatePesel(user, result, model);
        validateBankAccount(user, result, model);
        return result.hasErrors()
                ? Optional.of(result)
                : Optional.empty();
    }

    private void validateBankAccount(AppUserDto user, BindingResult result, Model model) {
        if (Objects.nonNull(user.getAccountBalance()) && user.getAccountBalance().getActualPln() < 0) {
            FieldError userAlreadyExist = new FieldError("appUser", "notPositiveValue",
                    messageSource.getMessage("error.notPositiveValue", null, Locale.getDefault()));
            result.addError(userAlreadyExist);
            model.addAttribute("notPositiveValue", messageSource.getMessage("error.notPositiveValue", null, Locale.getDefault()));
        }
    }

    private void validatePesel(AppUserDto user, BindingResult result, Model model) {
        if (Objects.nonNull(user.getPesel())) {
            PeselValidator peselValidator = new PeselValidator(user.getPesel());
            if (!peselValidator.isValid()) {
                FieldError userAlreadyExist = new FieldError("appUser", "invalidPesel",
                        messageSource.getMessage("error.invalidPesel", null, Locale.getDefault()));
                result.addError(userAlreadyExist);
                model.addAttribute("invalidPesel", messageSource.getMessage("error.invalidPesel", null, Locale.getDefault()));
            }
        }

    }

    private void validateExistingUser(AppUserDto user, BindingResult result, Model model) {

        Optional<AppUser> persistedUser = userService.findByPesel(user.getPesel());
        if (persistedUser.isPresent()) {
            FieldError userAlreadyExist = new FieldError("appUser", "userAlreadyExist",
                    messageSource.getMessage("error.userAlreadyExist", null, Locale.getDefault()));
            result.addError(userAlreadyExist);
            model.addAttribute("userAlreadyExist", messageSource.getMessage("error.userAlreadyExist", null, Locale.getDefault()));
        }
    }

    private void validateAge(AppUserDto user, BindingResult result, Model model) {
        LocalDate userBirthDate = user.getBirthDate();
        LocalDate minUserAge = LocalDate.now().minusYears(AppConstant.Dictionary.ADULT_MIN_AGE);
        if (Objects.nonNull(userBirthDate) && userBirthDate.isAfter(minUserAge)) {
            FieldError underagePerson = new FieldError("appUser", "underagePerson",
                    messageSource.getMessage("error.underagePerson", null, Locale.getDefault()));
            result.addError(underagePerson);
            model.addAttribute("underagePerson", messageSource.getMessage("error.underagePerson", null, Locale.getDefault()));
        }
    }
}
