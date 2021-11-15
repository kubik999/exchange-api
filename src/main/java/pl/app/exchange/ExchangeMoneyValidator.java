package pl.app.exchange;

import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Locale;
import java.util.Map;
import java.util.Optional;

@Component
@AllArgsConstructor
public class ExchangeMoneyValidator {

    private MessageSource messageSource;

    public Optional<BindingResult> validate(Map<ExchangeNominal, Double> moneyResourceMap, ExchangeCommand exchangeCommand, BindingResult result, Model model) {

        validateAmount(moneyResourceMap, exchangeCommand, result, model);
        validateIsPositive(moneyResourceMap, exchangeCommand, result, model);
        return result.hasErrors()
                ? Optional.of(result)
                : Optional.empty();
    }

    private void validateIsPositive(Map<ExchangeNominal, Double> moneyResourceMap, ExchangeCommand exchangeCommand, BindingResult result, Model model) {

        if (exchangeCommand.getValue() < 0) {
            FieldError userAlreadyExist = new FieldError("exchange", "notPositiveValue",
                    messageSource.getMessage("error.notPositiveValue", null, Locale.getDefault()));
            result.addError(userAlreadyExist);
            model.addAttribute("notPositiveValue", messageSource.getMessage("error.notPositiveValue", null, Locale.getDefault()));
        }
    }

    private void validateAmount(Map<ExchangeNominal, Double> moneyResourceMap, ExchangeCommand exchangeCommand, BindingResult result, Model model) {
        double value = moneyResourceMap.getOrDefault(exchangeCommand.getNominalFrom(), 0.0);
        if (value < exchangeCommand.getValue()) {
            FieldError userAlreadyExist = new FieldError("exchange", "moneyAmountExceeded",
                    messageSource.getMessage("error.moneyAmountExceeded", null, Locale.getDefault()));
            result.addError(userAlreadyExist);
            model.addAttribute("moneyAmountExceeded", messageSource.getMessage("error.moneyAmountExceeded", null, Locale.getDefault()));
        }
    }
}
