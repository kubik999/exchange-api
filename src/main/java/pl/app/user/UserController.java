package pl.app.user;

import lombok.AllArgsConstructor;
import pl.app.entity.BankAccount;
import pl.app.exchange.ExchangeCommand;
import pl.app.entity.AppUser;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import pl.app.exchange.ExchangeUsdService;
import pl.app.exchange.NbpUsdV1;
import pl.app.security.UserAuthService;
import pl.app.util.AppConstant;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class UserController {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    private UserService userService;
    private UserAuthService userAuthService;
    private UserServiceFacade userServiceFacade;
    private ExchangeUsdService exchangeUsdService;

    @GetMapping("/register")
    public String showForm(Model model) {
        model.addAttribute("appUserDto", new AppUserDto());
        return AppConstant.Template.REGISTER_PAGE;
    }

    @PostMapping("/save")
    public String addUser(@ModelAttribute @Valid AppUserDto userDto, BindingResult result, Model model) {
        Optional<AppUser> appUser = Optional.empty();
        try {
            appUser = userServiceFacade.saveUser(userDto, model, result);
        } catch (Exception e) {
            model.addAttribute("appException", e.getMessage());
        }
        return appUser.isEmpty()
                ? AppConstant.Template.REGISTER_PAGE
                : AppConstant.Template.SUCCESSFUL_REGISTERED_PAGE;
    }

    @GetMapping("/jpr-bank-app")
    public String showJprBankApp(Principal principal, Model model) {
        prepareModelForJprBankPage(model);
        return AppConstant.Template.JPR_BANK_PAGE;
    }

    @PostMapping("/jpr-bank-app/exchange")
    public String exchangePln(@ModelAttribute("exchange") @Valid ExchangeCommand exchange, BindingResult result, Model model) {
        try {
            userServiceFacade.exchangeLoggedUserMoney(exchange, result, model);
        } catch (Exception e) {
            model.addAttribute("appException", e.getMessage());
        } finally {
            prepareModelForJprBankPage(model);
        }
        return AppConstant.Template.JPR_BANK_PAGE;
    }

    private void prepareModelForJprBankPage(Model model) {

        Optional<AppUser> appUser = userAuthService.getLoggedUser();
        String name = appUser.isPresent()
                ? appUser.get().getName()
                : "";
        model.addAttribute("name", name);
        Optional<AppUser> user = userService.findByName(name);
        BankAccount accountBalance = user.isPresent()
                ? user.get().getAccountBalance()
                : new BankAccount();
        model.addAttribute("accountBalance", accountBalance);
        model.addAttribute("exchange", new ExchangeCommand());
        model.addAttribute("actualDolarRate", new ExchangeCommand());

        Optional<NbpUsdV1> nbpUsdV1 = exchangeUsdService.getUsdRate();
        double ask = nbpUsdV1.map(NbpUsdV1::getAsk).orElse(0.0);
        double bid = nbpUsdV1.map(NbpUsdV1::getBid).orElse(0.0);
        model.addAttribute("ask", ask);
        model.addAttribute("bid", bid);
    }
}