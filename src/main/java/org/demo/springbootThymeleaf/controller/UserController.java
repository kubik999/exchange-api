package org.demo.springbootThymeleaf.controller;

import org.demo.springbootThymeleaf.customValidation.AddUserValidator;
import org.demo.springbootThymeleaf.entity.Exchange;
import org.demo.springbootThymeleaf.entity.User;
import org.demo.springbootThymeleaf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Collection;
import java.util.Locale;

@Controller
public class UserController {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    private UserService userService;
    private RestTemplate restTemplate;

    @Autowired
    private MessageSource messageSource;

    //@Autowired
    public UserController(UserService userService, RestTemplate restTemplate) {
        this.userService = userService;
        this.restTemplate = restTemplate;
    }


    @GetMapping("/form")
    public String showForm(Model theModel) {
        User theUser = new User();
        theModel.addAttribute("user", theUser);
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
//        model.addAttribute("name", principal.getName());
//        //Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
//        Object details = SecurityContextHolder.getContext().getAuthentication().getDetails();
//        //model.addAttribute("authorities", authorities);
//        model.addAttribute("details", details);
//        model.addAttribute("stanKonta", 23423);
        Object forObject = restTemplate.getForObject("http://api.nbp.pl/api/exchangerates/rates/c/usd", Object.class);

        return "hello";
    }

    @PostMapping("/save")
    public String addUser(@ModelAttribute @Valid User theUser, BindingResult result, Model theModel, Locale locale) {

        AddUserValidator.customValidateAddUser(theUser, theModel, result, locale, messageSource);

        if (result.hasErrors()) {
            return "form";
        } else {
            userService.save(theUser);
            return "successfulAdd";
        }

    }
}