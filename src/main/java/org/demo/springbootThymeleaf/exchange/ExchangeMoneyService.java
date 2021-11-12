package org.demo.springbootThymeleaf.exchange;

import org.demo.springbootThymeleaf.entity.BankAccount;
import org.demo.springbootThymeleaf.shared.Exchange;

public interface ExchangeMoneyService {

    BankAccount exchange(BankAccount userBankAccount, Exchange toExchange);
}
