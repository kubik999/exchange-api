package pl.app.user;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.app.entity.AppUser;
import pl.app.entity.BankAccount;
import pl.app.exchange.ExchangeServiceStrategy;
import pl.app.exchange.ExchangeMoneyValidator;
import pl.app.exchange.ExchangeNominal;
import pl.app.exchange.ExchangeCommand;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import pl.app.security.UserAuthService;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
@AllArgsConstructor
public class UserServiceFacade {

    private UserService userService;
    private UserAuthService userAuthService;
    private UserValidator userValidator;
    private ExchangeMoneyValidator exchangeMoneyValidator;
    private PasswordEncoder passwordEncoder;
    private List<ExchangeServiceStrategy> exchangeMoneyServiceStrategies;

    public void exchangeLoggedUserMoney(ExchangeCommand exchangeCommand, BindingResult result, Model model) {

        AppUser loggedUser = userAuthService.getLoggedUser()
                .orElseThrow(() -> new RuntimeException("Nie znaleziono użytkownika"));

        AppUser user = userService.findByPesel(loggedUser.getPesel()).orElseThrow(() -> new RuntimeException("Nie znaleziono użytkownika"));
        BankAccount accountBalance = user.getAccountBalance();
        Map<ExchangeNominal, Double> moneyResourceMap = accountBalance.getMoneyResourceMap();
        Optional<BindingResult> validation = exchangeMoneyValidator.validate(moneyResourceMap, exchangeCommand, result, model);
        if (validation.isEmpty()) {
            exchangeMoneyServiceStrategies.stream()
                    .filter(strategy -> strategy.accept(exchangeCommand))
                    .findAny()
                    .orElseThrow(() -> new RuntimeException("Niedozwolona akcja wymiamy waluty"))
                    .exchange(accountBalance, exchangeCommand);
        }
    }

    public Optional<AppUser> saveUser(AppUserDto userDto, Model model, BindingResult result) {

        Optional<BindingResult> validation = userValidator.validate(userDto, model, result);
        if (validation.isEmpty()) {
            encodePassword(userDto);
            AppUser appUser = UserTransformer.toDomain(userDto);
            return Optional.of(userService.saveUser(appUser));
        }
        return Optional.empty();
    }

    private void encodePassword(AppUserDto userDto) {
        if (Objects.nonNull(userDto.getPassword())) {
            userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }
    }
}
