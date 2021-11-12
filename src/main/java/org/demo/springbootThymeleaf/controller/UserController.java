package org.demo.springbootThymeleaf.controller;

import org.demo.springbootThymeleaf.customValidation.AddUserValidator;
import org.demo.springbootThymeleaf.entity.BankAccount;
import org.demo.springbootThymeleaf.user.UserServiceFacade;
import org.demo.springbootThymeleaf.shared.Exchange;
import org.demo.springbootThymeleaf.entity.AppUser;
import org.demo.springbootThymeleaf.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Locale;

@Controller
public class UserController {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    private UserService userService;
    private UserServiceFacade userServiceFacade;

    @Autowired
    private MessageSource messageSource;

    public UserController(UserService userService, UserServiceFacade userServiceFacade) {
        this.userService = userService;
        this.userServiceFacade = userServiceFacade;
    }

    @GetMapping("/form")
    public String showForm(Model theModel) {
        AppUser theAppUser = new AppUser();
        theModel.addAttribute("user", theAppUser);
        return "form";
    }

    @GetMapping("/hello")
    public String hello(Principal principal, Model model) {
        model.addAttribute("name", principal.getName());
        //Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        Object details = SecurityContextHolder.getContext().getAuthentication().getDetails();
        //model.addAttribute("authorities", authorities);
        model.addAttribute("details", details);
        model.addAttribute("stanKonta", userService.findByName(principal.getName()).get().getStanKonta());
        model.addAttribute("exchange", new Exchange());
        return "hello";
    }

    @PostMapping("/hello/exchange")
    public String exchange(@ModelAttribute("exchange") Exchange exchange, BindingResult result, Model theModel, Locale locale) {
        BankAccount bankAccount = userServiceFacade.exchengeMoney(exchange);
        //theModel.addAttribute("name", principal.getName());
        theModel.addAttribute("stanKonta", bankAccount);
        return "hello";
    }

    @PostMapping("/save")
    public String addUser(@ModelAttribute @Valid AppUser theAppUser, BindingResult result, Model theModel, Locale locale) {

        AddUserValidator.customValidateAddUser(theAppUser, theModel, result, locale, messageSource);

        if (result.hasErrors()) {
            return "form";
        } else {
            userService.save(theAppUser);
            return "successfulAdd";
        }

    }
}