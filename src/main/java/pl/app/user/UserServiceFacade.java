package pl.app.user;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.app.entity.AppUser;
import pl.app.entity.BankAccount;
import pl.app.exchange.ExchangeMoneyServiceStrategy;
import pl.app.exchange.ExchangeMoneyValidator;
import pl.app.exchange.ExchangeNominal;
import pl.app.exchange.ExchangeCommand;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
@AllArgsConstructor
public class UserServiceFacade {

    private UserService userService;
    private RestTemplate restTemplate;
    private UserValidator userValidator;
    private PasswordEncoder passwordEncoder;
    private List<ExchangeMoneyServiceStrategy> exchangeMoneyServiceStrategies;

    public BankAccount exchengeMoney(ExchangeCommand exchangeCommand) {

        AppUser user = userService.getUser()
                .orElseThrow(() -> new RuntimeException("nie zalogoway"));

        BankAccount accountBalance = user.getAccountBalance();
        Map<ExchangeNominal, Double> moneyResourceMap = accountBalance.getMoneyResourceMap();
        ExchangeMoneyValidator.validate(moneyResourceMap, exchangeCommand);
        exchangeMoneyServiceStrategies.stream()
                .filter(strategy -> strategy.accept(exchangeCommand))
                .findAny()
                .orElseThrow(() -> new RuntimeException("nie ma takiej stargi"))
                .exchange(accountBalance, exchangeCommand);
        return accountBalance;
    }

    public Optional<AppUser> saveUser(AppUser user, Model theModel, BindingResult result, Locale locale) {

        userValidator.customValidateAddUser(user, theModel, result, locale);
        encodePassword(user);
        return result.hasErrors()
                ? Optional.empty()
                : Optional.of(userService.saveUser(user));
    }

    private void encodePassword(AppUser user) {
        if (Objects.nonNull(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
    }
}
