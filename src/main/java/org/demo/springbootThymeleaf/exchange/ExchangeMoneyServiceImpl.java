package org.demo.springbootThymeleaf.exchange;

import org.demo.springbootThymeleaf.entity.BankAccount;
import org.demo.springbootThymeleaf.shared.Exchange;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.LinkedHashMap;

@Service
@Transactional
public class ExchangeMoneyServiceImpl implements ExchangeMoneyService {

    private RestTemplate restTemplate;

    public ExchangeMoneyServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public BankAccount exchange(BankAccount userBankAccount, Exchange toExchange) {

        double actualPln = userBankAccount.getActualPln();
        double actualUsd = userBankAccount.getActualUsd();
        double toExchangePln = toExchange.getToExchangePln();

        userBankAccount.setActualPln(actualPln - toExchangePln);
        double ileDolcow = byUsd(toExchangePln);
        userBankAccount.setActualUsd(actualUsd + ileDolcow);

        return userBankAccount;
    }

    private double byUsd(double toExchangePln) {

        LinkedHashMap forObject = restTemplate.getForObject("http://api.nbp.pl/api/exchangerates/rates/c/usd", LinkedHashMap.class);
        ArrayList rates = (ArrayList)forObject.get("rates");
        LinkedHashMap linkedHashMap = (LinkedHashMap) rates.get(0);
        Double cenaDolara = (Double) linkedHashMap.get("ask");

       return toExchangePln / cenaDolara;



    }
}
