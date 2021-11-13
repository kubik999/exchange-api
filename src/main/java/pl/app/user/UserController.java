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
import pl.app.util.AppConstant;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Locale;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class UserController {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }
    private UserService userService;
    private UserServiceFacade userServiceFacade;

    @GetMapping("/register")
    public String showForm(Model model) {
        model.addAttribute("appUser", new AppUser());
        return AppConstant.Template.REGISTER_PAGE;
    }

    @PostMapping("/save")
    public String addUser(@ModelAttribute @Valid AppUser user, BindingResult result, Model model, Locale locale) {
        Optional<AppUser> appUser = userServiceFacade.saveUser(user, model, result, locale);
        return appUser.isEmpty()
                ? AppConstant.Template.REGISTER_PAGE
                : AppConstant.Template.SUCCESSFUL_REGISTERED_PAGE;
    }

    @GetMapping("/jpr-bank-app")
    public String showJprBankApp(Principal principal, Model model) {
        model.addAttribute("name", principal.getName());
        model.addAttribute("accountBalance", userService.findByName(principal.getName()).get().getAccountBalance());
        model.addAttribute("exchange", new ExchangeCommand());
        return AppConstant.Template.JPR_BANK_PAGE;
    }

    @PostMapping("/jpr-bank-app/exchange")
    public String exchangePln(@ModelAttribute("exchange") ExchangeCommand exchange, BindingResult result, Model theModel, Locale locale) {
        BankAccount bankAccount = userServiceFacade.exchengeMoney(exchange);
        theModel.addAttribute("accountBalance", bankAccount);
        return AppConstant.Template.JPR_BANK_PAGE;
    }
}