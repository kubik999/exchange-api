package org.demo.springbootThymeleaf.user;

import org.demo.springbootThymeleaf.entity.AppUser;
import org.demo.springbootThymeleaf.entity.BankAccount;
import org.demo.springbootThymeleaf.exchange.ExchangeMoneyService;
import org.demo.springbootThymeleaf.exchange.ExchangeMoneyValidator;
import org.demo.springbootThymeleaf.shared.Exchange;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserServiceFacade {

    private UserService userService;
    private RestTemplate restTemplate;
    private ExchangeMoneyService exchangeMoneyService;

    public UserServiceFacade(UserService userService, RestTemplate restTemplate, ExchangeMoneyService exchangeMoneyService) {
        this.userService = userService;
        this.restTemplate = restTemplate;
        this.exchangeMoneyService = exchangeMoneyService;
    }

    public BankAccount exchengeMoney(Exchange exchange) {

        AppUser user = userService.getUser()
                .orElseThrow(() -> new RuntimeException("nie zalogoway"));

        BankAccount stanKonta = user.getStanKonta();

        ExchangeMoneyValidator.validate(stanKonta.getActualPln() , exchange.getToExchangePln());
        //ExchangeMoneyValidator.validate2(stanKonta.getActualUsd() , exchange.getToExchangeUsd());

        return exchangeMoneyService.exchange(stanKonta, exchange);


    }
}
